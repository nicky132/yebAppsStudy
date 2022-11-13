package com.xxxx.server.mapper;

import com.xxxx.server.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cdh
 * @since 2022-02-12
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id查询菜单列表
     * @param id
     * @return
     */
     List<Menu> getMenusByAdminId(Integer id);
}
