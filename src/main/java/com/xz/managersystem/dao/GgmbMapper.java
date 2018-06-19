package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TGgmb;
import com.xz.managersystem.entity.TGgym;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface GgmbMapper extends Mapper<TGgmb>, MySqlMapper<TGgmb> {

    @Select("SELECT COUNT(1) FROM t_ggmb ")
    int getVisibleCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des \n" +
            "  FROM t_ggmb t LIMIT #{start}, #{rows}  \n")
    List<TGgmb> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des \n" +
            "  FROM t_ggmb t WHERE t.id =#{id} LIMIT 1")
    TGgmb findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des \n" +
            "  FROM t_ggmb t ")
    List<TGgmb> selectVisibleAll();

}
