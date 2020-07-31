package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContext;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.yinhua.entity.ModelFile;
import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllModel;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.mobile.mapper.*;
import cn.stylefeng.guns.yinhua.service.ModelService;
import cn.stylefeng.guns.yinhua.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 款式业务类
 */
@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ModelImageMapper modelImageMapper;
    @Autowired
    private ModelInfoMapper modelInfoMapper;
    @Autowired
    private ModelColorMapper modelColorMapper;
    @Autowired
    private ModelNoteMapper modelNoteMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelConstLogMapper modelCOnstLogMapper;
    @Autowired
    private ModelFileMapper modelFileMapper;
    @Autowired
    private CustomerMapper customerMapper;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 获取款式列表
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Model> getModelList(Long page, Long pageSize, String name, Integer status) {
        if(page ==null || pageSize==null){
            throw new OutExcetion("分页参数异常");
        }
        QueryWrapper<Model> queryWrapper = new QueryWrapper<>();
        if(status != null && status >= 0){
            queryWrapper = new QueryWrapper<>();
            if(status == 0){
                queryWrapper.apply("num not in (select model_id from orders group by model_id)");
            }else {
                queryWrapper.eq("status",status);
            }
        }
        if(name!=null && name !=""){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name",name)
                    .or().like("num",name)
                    .or().like("model_num",name)
                    .or().like("customer",name);
        }else{
            queryWrapper.eq("created_user",LoginContextHolder.getContext().getUserId().toString()).or().isNull("created_user");
        }
        Page<Model> modelPage = modelMapper.selectMyPage(new Page<Model>(page, pageSize),LoginContextHolder.getContext().getUserId().toString(),name ,queryWrapper);
        return modelPage;
    }

    /**
     * 款式数据统计
     * @return
     */
    @Override
    public RestOrderCount getDataCount() {
        RestOrderCount modelData = modelMapper.getModelData();
        return modelData;
    }

    /**
     * 款式信息添加 修改
     * @param model
     * @return
     */
    @Override
    @Transactional
    public Model addModel(Model model) {
        model.setCreatedTime(new Date());
        model.setStatus(0);
        model.setCreatedUser(LoginContextHolder.getContext().getUserId());
        if(StringUtils.isEmpty(model.getNum())){
            model.setNum(new Long(simpleDateFormat.format(new Date()).substring(2,14)));
            modelMapper.insert(model);
            return model;
        }
        if(StringUtils.isEmpty(model.getDrawNum())){
            model.setDrawNum(model.getNum());
        }
        QueryWrapper<Model> queryWrapper = new QueryWrapper();
        /* 判断是客户名字保存 则找该客户的编号*/
        if(model.getCustomer()!= null && model.getCustomer().matches("[\u4E00-\u9FA5]+")){
            Customer name = customerMapper.selectOne(new QueryWrapper<Customer>().eq("name", model.getCustomer()));
            model.setCustomer(name.getNum());
        }
        queryWrapper.eq("num",model.getNum());
        int update = modelMapper.update(model,queryWrapper);
        if(update == 0){
            modelMapper.insert(model);
        }
        return model;
    }

    /**
     * 裁片信息修改
     * @param modelInfo
     * @param imageId
     * @return
     */
    @Override
    public ModelInfo updateModelInfo(ModelInfo modelInfo, Integer imageId) {
        if(Arrays.asList(imageId)!=null&&Arrays.asList(imageId).size()!=0){
            modelMapper.updateImageId(modelInfo.getNum(),modelInfo.getId(), imageId);
        }
        modelInfoMapper.updateById(modelInfo);
        return modelInfo;
    }

    /**
     * 删除单个裁片和图片组合
     * @param id
     */
    @Override
    @Transactional
    public void deleteModelInfoAndImage(Integer id) {
        modelImageMapper.delete(new QueryWrapper<ModelImage>().eq("modelinfo_id",id));
        modelInfoMapper.deleteById(id);
    }

    /**
     * 附件添加
     * @param modelFile
     */
    @Override
    public void addModelFile(ModelFile modelFile) {
        modelFileMapper.insert(modelFile);
    }

    /**
     * 附件删除
     * @param id
     */
    @Override
    public void deleteModelFile(Integer id) {
        modelFileMapper.deleteById(id);
    }

    /**
     * 款式附件获取
     * @param num
     * @return
     */
    @Override
    public List<ModelFile> getModelFile(Long num) {
        List<ModelFile> modelFiles = modelFileMapper.selectList(new QueryWrapper<ModelFile>().eq("num", num));
        return modelFiles;
    }

    /**
     * 删除款式配色表
     * @param id
     */
    @Override
    public void deleteModelColorForId(Integer id) {
        modelColorMapper.deleteById(id);
    }

    /**
     * 裁片图片添加 返回图片id
     * @param modelImage
     * @return
     */
    @Override
    public Integer addModelImage(ModelImage modelImage) {
        modelImageMapper.insert(modelImage);
        return modelImage.getId();
    }


    /**
     * 图片删除
     * @param url
     */
    @Override
    public void deleteModelImage(String url) {
        QueryWrapper<ModelImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        int delete = modelImageMapper.delete(queryWrapper);
        if(delete == 0){
            throw new OutExcetion("图片删除失败");
        }
    }

    /**
     * 图片信息修改
     * @param modelImage
     */
    @Override
    public void updateModelImage(ModelImage modelImage) {
        int update = modelImageMapper.updateById(modelImage);
        if(update==0){
            throw new OutExcetion("图片信息修改失败");
        }
    }

    /**
     * 添加裁片信息
     * @param modelInfo
     */
    @Override
    @Transactional
    public ModelInfo addModelInfo(ModelInfo modelInfo,Integer imageId) {
        if(StringUtils.isEmpty(modelInfo.getNum())){
            modelInfo.setNum(new Long(simpleDateFormat.format(new Date()).substring(2,14)));
        }
        modelInfo.setCreatedTime(new Date());
        modelInfoMapper.insert(modelInfo);
        if(modelMapper.selectOne(new QueryWrapper<Model>().eq("num",modelInfo.getNum()))==null){
            Model model = new Model();
            model.setNum(modelInfo.getNum());
            model.setName("未设置名称");
            model.setLinlingFrom(2);
            addModel(model);
        }
        if(Arrays.asList(imageId)!=null&&Arrays.asList(imageId).size()!=0){
            modelMapper.updateImageId(modelInfo.getNum(),modelInfo.getId(), imageId);
        }
        return modelInfo;
    }

    /**
     * 单个裁片信息查看
     * @param id
     * @return
     */
    @Override
    public ModelCut getModelInfoById(Integer id) {
        ModelInfo modelInfo = modelInfoMapper.selectOne(new QueryWrapper<ModelInfo>().eq("id", id));
        List<ModelImage> modelinfo_id = modelImageMapper.selectList(new QueryWrapper<ModelImage>().eq("modelinfo_id", id));
        ModelCut modelCut = new ModelCut();
        modelCut.setModelImage(modelinfo_id);
        modelCut.setModelInfo(modelInfo);
        return modelCut;
    }

    /**
     * 裁片信息和图片信息列表获取
     * @param num
     */
    @Override
    public List<ModelCut> getModelInfo(Long num) {
        if(num == null){
            return null;
        }
        QueryWrapper<ModelInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("num",num);
        List<ModelInfo> modelInfos = modelInfoMapper.selectList(queryWrapper);
        List<ModelCut> a = new ArrayList<>();
        for(ModelInfo modelInfo:modelInfos){
            ModelCut m = new ModelCut();
            m.setModelInfo(modelInfo);
            List<ModelImage> modelImages = modelImageMapper.selectList(new QueryWrapper<ModelImage>().eq("modelinfo_id", modelInfo.getId()));
            m.setModelImage(modelImages);
            a.add(m);
        }
        return a;
    }

    /**
     * 款式配色表获取
     * @param num
     * @return
     */
    @Override
    public List<ModelColor> getModelColor(Long num) {
        if(num == null){
            return null;
        }
        QueryWrapper<ModelColor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_id",num);
        List<ModelColor> modelColors = modelColorMapper.selectList(queryWrapper);
        return modelColors;
    }

    /**
     * 款式信息获取
     * @param num
     * @return
     */
    @Override
    public Model getModel(Long num) {
        if(num == null){
            return null;
        }
        LoginContext context = LoginContextHolder.getContext();
        String s = "";
        if(false == context.hasLogin()){
            s = "0";
        }else {
            s = context.getUserId().toString();
        }
        Model model = modelMapper.selectModelByNum(s,num);

        if(model == null){
            throw new OutExcetion("获取失败，请重试");
        }
        return model;
    }

    /**
     * 款式修改
     * @param model
     */
    @Override
    @Transactional
    public void updateModel(Model model) {
        modelMapper.updateById(model);
    }

    /**
     * 款式复制
     * @param num
     */
    @Override
    public Long copyModel(Long num) {
        /* 款式复制 */
        Long aLong = new Long(simpleDateFormat.format(new Date()).substring(2, 14));
        Model model1 = modelMapper.selectOne(new QueryWrapper<Model>().eq("num", num));
        model1.setNum(aLong);
        model1.setId(null);
        model1.setCustomer(null);
        model1.setCustomerName(null);
        model1.setCreatedTime(new Date());
        model1.setCreatedUser(LoginContextHolder.getContext().getUserId());
        modelMapper.insert(model1);
        /* 裁片信息复制 */
        List<ModelInfo> num1 = modelInfoMapper.selectList(new QueryWrapper<ModelInfo>().eq("num", num));
        num1.forEach(
                modelInfo ->{
                    /* 裁片图片复制 */
                    ModelImage modelinfo_id = modelImageMapper.selectOne(new QueryWrapper<ModelImage>().eq("modelinfo_id", modelInfo.getId()));
                    modelInfo.setNum(aLong);
                    modelInfo.setId(null);
                    modelInfo.setCreatedTime(new Date());
                    modelInfo.setMuchs(null);
                    modelInfoMapper.insert(modelInfo);
                    if(modelinfo_id != null ){
                        modelinfo_id.setModelinfoId(modelInfo.getId());
                        modelinfo_id.setId(null);
                        modelImageMapper.insert(modelinfo_id);
                    }

                }
        );
        /* 款式配色表复制*/
        List<ModelColor> modelColors = modelColorMapper.selectList(new QueryWrapper<ModelColor>().eq("model_id", num));
        if(modelColors.size() != 0 ){
            modelColors.forEach(
                    modelColor -> {
                        modelColor.setId(null);
                        modelColor.setModelId(aLong);
                        modelColorMapper.insert(modelColor);
                    }
            );
        }
        return aLong;
    }

    /**
     * 配色表数据添加
     * @param modelColor
     * @return
     */
    @Override
    public ModelColor addModelColor(ModelColor modelColor) {
        if(StringUtils.isEmpty(modelColor.getModelId())){
            modelColor.setModelId(new Long(simpleDateFormat.format(new Date()).substring(2,14)));
            modelColorMapper.insert(modelColor);
            return modelColor;
        }
        QueryWrapper<ModelColor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_id",modelColor.getModelId());
        modelColorMapper.insert(modelColor);
        return modelColor;
    }

    /**
     * 删除某一个配色表数据
     * @param color
     */
    @Override
    public void deleteModelColor(String color) {
        QueryWrapper<ModelColor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("color",color);
        int delete = modelColorMapper.delete(queryWrapper);
        if(delete == 0){
            throw new OutExcetion("删除失败");
        }

    }

    /**
     * 裁片图片获取
     * @param type
     * @return
     */
    @Override
    public ModelImage getModelImage(Integer id,Integer type) {
        ModelImage modelImage = modelImageMapper.selectOne(new QueryWrapper<ModelImage>().eq("id", id).eq("type", type));
        return modelImage;
    }

    /**
     * 修改配色表数据
     * @param modelColor
     */
    @Override
    public void updateModelColor(ModelColor modelColor) {
        modelColorMapper.updateById(modelColor);
    }

    /**
     * 添加款式日志
     * @param modelNote
     */
    @Override
    public void addModelNote(ModelNote modelNote) {
        modelNote.setNum(modelNote.getNum().split("-")[0]);
        modelNote.setCreatedTime(new Date());
        modelNote.setName(LoginContextHolder.getContext().getUser().getName());
        modelNoteMapper.insert(modelNote);
    }

    /**
     * 删除款式日志
     * @param id
     */
    @Override
    public void deleteModelNote(Integer id) {
        modelNoteMapper.deleteById(id);
    }

    /**
     * 获取款式备注
     * @param orderNum
     * @return
     */
    @Override
    public String getModelNoteByOrderNum(String orderNum) {
        String note = modelMapper.getModelNote(orderNum.split("-")[0]);
        return note;
    }

    /**
     * 保存款式备注
     * @param orderNum
     * @param note
     */
    @Override
    public void saveModelNote(String orderNum, String note) {
        modelMapper.saveModelNote(orderNum.split("-")[0],note);
    }

    /**
     * 款式日志获取
     * @param num
     * @return
     */
    @Override
    public List<ModelNote> getModelNote(Long num) {
        if(num == null){
            return null;
        }
        QueryWrapper<ModelNote> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("num",num);
        List<ModelNote> modelNotes = modelNoteMapper.selectList(queryWrapper.orderByDesc("created_time"));
        return modelNotes;
    }

    /**
     * 款式详情获取
     * @param num
     * @return
     */
    @Override
    public RestAllModel getAllModel(Long num) {
        RestAllModel restAllModel = new RestAllModel();
        restAllModel.setModelInfo(getModelInfo(num))
        .setModelInfo(getModelInfo(num))
        .setModel(getModel(num))
        .setModelNote(getModelNote(num));

        return restAllModel;
    }

    /**
     * 裁片信息读取
     * @param num
     * @return
     */
    @Override
    public List<ModelInfo> getModelCut(Long num) {
        List<ModelInfo> num1 = modelInfoMapper.selectList(new QueryWrapper<ModelInfo>().eq("num", num));
        return num1;
    }

    /**
     * 裁片价格修改
     * @param id
     * @param muchs
     */
    @Override
    @Transactional
    public void changeModelInfoMuchs(Integer id, Double muchs) {
        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setMuchs(muchs);
        ModelConstLog modelConstLog = new ModelConstLog();
        modelConstLog.setConsts(muchs).setCreatedTime(new Date()).setNum(id);
        modelCOnstLogMapper.insert(modelConstLog);
        modelInfoMapper.update(modelInfo,new QueryWrapper<ModelInfo>().eq("id",id));
    }

    /**
     * 删除款式
     * @param num
     */
    @Override
    @Transactional
    public void deleteModel(Long num) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("num",num);
        modelMapper.delete(queryWrapper);
        modelInfoMapper.delete(queryWrapper);
        modelCOnstLogMapper.delete(queryWrapper);
        modelNoteMapper.delete(queryWrapper);
        modelColorMapper.delete(new QueryWrapper<ModelColor>().eq("model_id",num));
        modelImageMapper.delete(queryWrapper);
        Integer s = orderService.checkOrderByModelNum(num);
        if(s > 0 ){
            throw new OutExcetion("该款式下存在订单，不能删除");
        }
        orderService.deleteAllOrder(num);
    }
}
