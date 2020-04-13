package com.baizhi.zw.serviceimpl;

import com.baizhi.zw.annotation.AddCaChe;
import com.baizhi.zw.annotation.DelCaChe;
import com.baizhi.zw.dao.LogDAO;
import com.baizhi.zw.entity.Log;
import com.baizhi.zw.entity.LogExample;
import com.baizhi.zw.service.LogService;
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
public class LogServiceImpl implements LogService {
    @Autowired
    LogDAO logDAO;

    @AddCaChe
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();

        //总条数:records
        LogExample logExample = new LogExample();
        int records = logDAO.selectCountByExample(logExample);
        map.put("records", records);
        //总页数:total  总条数/每页展示的条数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        map.put("total", total);
        //当前页:page
        map.put("page", page);
        //数据:rows
        //参数:从第几条数据展示,每页展示几条数据
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Log> logs = logDAO.selectByRowBounds(new Log(), rowBounds);
        map.put("rows", logs);

        return map;
    }

    @DelCaChe
    @Override
    public void add(Log log) {
        String uuid = UUIDUtil.getUUID();
        log.setId(uuid);

        logDAO.insert(log);
    }
}
