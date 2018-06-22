package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TMbxx;
import com.xz.managersystem.entity.TSbxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SbglMapper extends Mapper<TSbxx>, MySqlMapper<TSbxx> {

    @Select("SELECT COUNT(1) FROM t_sb WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_sb")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_id,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as fz_label,\n" +
            "  c.label as ym_label \n" +
            "  FROM t_sb t " +
            "  LEFT JOIN t_fz b on t.fz_id = b.id " +
            "  LEFT JOIN t_ym c on t.ym_id = c.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC")
    List<TSbxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_id,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as fz_label,\n" +
            "  c.label as ym_label \n" +
            "  FROM t_sb t " +
            "  LEFT JOIN t_fz b on t.fz_id = b.id " +
            "  LEFT JOIN t_ym c on t.ym_id = c.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TSbxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_id,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as fz_label,\n" +
            "  c.label as ym_label \n" +
            "  FROM t_sb t " +
            "  LEFT JOIN t_fz b on t.fz_id = b.id " +
            "  LEFT JOIN t_ym c on t.ym_id = c.id " +
            "  WHERE t.is_deleted = 0 AND t.id = #{id} LIMIT 1")
    TSbxx findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.fz_id,\n" +
            "  t.ym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as fz_label,\n" +
            "  c.label as ym_label \n" +
            "  FROM t_sb t " +
            "  LEFT JOIN t_fz b on t.fz_id = b.id " +
            "  LEFT JOIN t_ym c on t.ym_id = c.id " +
            "  WHERE t.is_deleted = 0 AND t.label = #{label} LIMIT 1")
    TSbxx findOneByName(String label);

    @Insert("INSERT INTO t_sb(label, des, location, fz_id, ym_id) VALUES(#{label}, #{des}, #{location}, #{fzId}, #{ymId})")
    int insert(TSbxx sbxx);

    @Update("update t_sb t set t.des = #{des}, t.location = #{location}, t.fz_id = #{fzId}, t.ym_id = #{ymId} where t.id = #{id}")
    int updateByPrimaryKeySelective(TSbxx sbxx);

    @Delete("update t_sb t set t.is_deleted = 1 where t.id = #{id}")
    int deleteById(Integer id);

}
