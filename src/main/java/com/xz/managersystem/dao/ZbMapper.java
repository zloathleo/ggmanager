package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TZbxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ZbMapper extends Mapper<TZbxx>, MySqlMapper<TZbxx> {

    @Select("SELECT COUNT(1) FROM t_zb WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_zb AND type = #{type}")
    int getAllCount(String type);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zb t WHERE t.is_deleted = 0" +
            "  ORDER BY t.create_time DESC ")
    List<TZbxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zb t WHERE t.is_deleted = 0" +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TZbxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zb t WHERE t.is_deleted = 0 " +
            "  AND t.label = #{label}")
    TZbxx selectByName(String label);

    @Insert("INSERT INTO t_zb(label, des) VALUES(#{label}, #{des})")
    int insert(TZbxx zbxx);

    @Update("UPDATE t_zb t SET t.des = #{des}, t.update_time=NOW() WHERE t.label = #{label} AND t.is_deleted = 0")
    int updateByName(TZbxx zbxx);

    @Delete("UPDATE t_zb t SET t.is_deleted = 1 WHERE t.label = #{label}")
    int deleteByName(String label);
}
