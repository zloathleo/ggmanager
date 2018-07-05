package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TYmxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface YmMapper extends Mapper<TYmxx>, MySqlMapper<TYmxx> {

    @Select("SELECT COUNT(1) FROM t_ym WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.content,\n" +
            "  t.des,\n" +
            "  t.create_time,\n" +
            "  t.update_time\n" +
            "  FROM t_ym t " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC ")
    List<TYmxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.content,\n" +
            "  t.des,\n" +
            "  t.create_time,\n" +
            "  t.update_time\n" +
            "  FROM t_ym t WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TYmxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.content,\n" +
            "  t.des,\n" +
            "  t.create_time,\n" +
            "  t.update_time\n" +
            "  FROM t_ym t WHERE t.is_deleted = 0" +
            "  AND t.label = #{label} LIMIT 1")
    TYmxx selectByName(String label);

    @Insert("INSERT INTO t_ym(label, content, des) VALUES(#{label}, #{content}, #{des})")
    int insert(TYmxx ymxx);

    @Update("UPDATE t_ym t SET t.content = #{content}, t.des = #{des}, t.update_time=NOW() " +
            "WHERE t.label = #{label} AND is_deleted = 0")
    int updateByName(TYmxx ymxx);

    @Delete("update t_ym t set t.is_deleted = 1 where t.label = #{label}")
    int deleteByName(String label);
}
