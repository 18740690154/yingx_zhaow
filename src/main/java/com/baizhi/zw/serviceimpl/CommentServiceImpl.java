package com.baizhi.zw.serviceimpl;

import com.baizhi.zw.annotation.AddCaChe;
import com.baizhi.zw.dao.CommentDAO;
import com.baizhi.zw.entity.Comment;
import com.baizhi.zw.entity.CommentExample;
import com.baizhi.zw.service.CommentService;
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
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDAO commentDAO;

    @AddCaChe
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();

        //总条数:records
        CommentExample commentExample = new CommentExample();
        int records = commentDAO.selectCountByExample(commentExample);
        map.put("records", records);
        //总页数:total  总条数/每页展示的条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页:page
        map.put("page", page);
        //数据:rows
        //参数:从第几条数据展示,每页展示几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Comment> comments = commentDAO.selectByRowBounds(new Comment(), rowBounds);
        map.put("rows", comments);

        return map;
    }
}
