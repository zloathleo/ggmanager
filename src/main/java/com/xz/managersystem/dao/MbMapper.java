package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TMbxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface MbMapper extends Mapper<TMbxx>, MySqlMapper<TMbxx> {

    @Select("SELECT COUNT(1) FROM t_mb WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.demo \n" +
            "  FROM t_mb t WHERE t.is_deleted = 0")
    List<TMbxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.demo \n" +
            "  FROM t_mb t WHERE t.is_deleted = 0 LIMIT #{start}, #{rows}")
    List<TMbxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.demo \n" +
            "  FROM t_mb t WHERE t.id = #{id} LIMIT 1")
    TMbxx findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.demo \n" +
            "  FROM t_mb t WHERE t.label = #{label} LIMIT 1")
    TMbxx findOneByName(String label);

    @Insert("INSERT INTO t_mb(label, des, type, demo) VALUES(#{label}, #{des}, #{type}, #{demo})")
    int insert(TMbxx mbxx);

    @Update("update t_mb t set t.des = #{des}, t.type = #{type}, t.demo = #{demo} where t.id = #{id}")
    int updateByPrimaryKeySelective(TMbxx mbxx);

    @Delete("update t_mb t set t.is_deleted = 1 where t.id = #{id}")
    int deleteById(Integer id);
}
