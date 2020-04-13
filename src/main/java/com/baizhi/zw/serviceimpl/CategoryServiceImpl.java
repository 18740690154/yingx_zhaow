package com.baizhi.zw.serviceimpl;

import com.baizhi.zw.annotation.AddCaChe;
import com.baizhi.zw.annotation.AddLog;
import com.baizhi.zw.annotation.DelCaChe;
import com.baizhi.zw.dao.CategoryDAO;
import com.baizhi.zw.dao.VideoDAO;
import com.baizhi.zw.entity.Category;
import com.baizhi.zw.entity.CategoryExample;
import com.baizhi.zw.entity.VideoExample;
import com.baizhi.zw.service.CategoryService;
import com.baizhi.zw.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    VideoDAO videoDAO;

    @AddCaChe
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryByLevelsAndPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //一级类别分页展示
        //总条数:records
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo("1");
        int records = categoryDAO.selectCountByExample(example);
        map.put("records", records);
        //总页数:total
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页数:page
        map.put("page", page);
        //数据:rows  根据级别查询
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Category> categorys = categoryDAO.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows", categorys);
        return map;
    }


    //添加类别
    @DelCaChe
    @AddLog(value = "添加类别")
    @Override
    public void add(Category category) {
        //判断添加的是一级类别还是二级类别
        if (category.getParentId() == null) {
            //一级类别
            category.setLevels("1");
        } else {
            //二级类别
            category.setLevels("2");
        }
        String uuid = UUIDUtil.getUUID();
        category.setId(uuid);

        categoryDAO.insert(category);
    }

    @DelCaChe
    //修改类别
    @AddLog(value = "修改类别")
    @Override
    public void update(Category category) {
        categoryDAO.updateByPrimaryKeySelective(category);
    }

    //删除类别
    @AddLog(value = "删除类别")
    @Override
    public Map<String, Object> delete(Category category) {
        HashMap<String, Object> map = new HashMap<>();

        //根据类别Id查询类别信息
        Category cate = categoryDAO.selectOne(category);
        //判断删除类别的级别
        if (cate.getLevels() == "1") {
            //该级别为一级类别，以一级类别的id作为ParentId来查询二级类别
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryDAO.selectCountByExample(example);
            //判断该一级类别下是否有二级类别
            if (count == 0) {
                //不存在，直接删除
                categoryDAO.deleteByPrimaryKey(category);
                map.put("message", "删除成功");

            } else {
                //存在，返回提示信息，无法删除
                map.put("message", "该类别有子类别，无法删除");
            }
        } else {
            //该级别为二级类别，根据二级类别id查询视频
            VideoExample videoExample = new VideoExample();
            videoExample.createCriteria().andCategoryIdEqualTo(category.getId());
            int count = videoDAO.selectCountByExample(videoExample);
            //判断该级别下是否存在视频
            if (count == 0) {
                //不存在，直接删除
                categoryDAO.deleteByPrimaryKey(category);
                map.put("message", "删除成功");
            } else {
                //存在，返回提示信息，无法删除
                map.put("message", "该类别下存在视频,无法是你出");
            }
        }
        return map;
    }

    @AddCaChe
    //根据一级类别Id查询二级类别
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByParentIdAndPage(Integer page, Integer rows, String parentId) {
        HashMap<String, Object> map = new HashMap<>();

        //二级类别分页展示

        //总条数:records
        CategoryExample example = new CategoryExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        int records = categoryDAO.selectCountByExample(example);
        map.put("records", records);
        //总页数:total
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页:page
        map.put("page", page);
        //数据：rows 根据一级类别id查询
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Category> categories = categoryDAO.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows", categories);
        return map;
    }

    @AddCaChe
    @Override
    public List<Category> queryAllCategory() {
        List<Category> categories = categoryDAO.queryAllCategory();
        return categories;
    }
}
