package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TRmsg;
import com.xz.managersystem.entity.TRmsgInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface RmsgMapper extends Mapper<TRmsg>, MySqlMapper<TRmsg> {

    @Select("SELECT COUNT(1) FROM t_rmsg WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_rmsg")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.zy_id,\n" +
            "  t.fz_id,\n" +
            "  t.start_time,\n" +
            "  t.end_time,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as zy_label,\n" +
            "  c.label as fz_label \n" +
            "  FROM t_rmsg t " +
            "  LEFT JOIN t_zy b on t.zy_id = b.id " +
            "  LEFT JOIN t_fz c on t.fz_id = c.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC")
    List<TRmsg> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.zy_id,\n" +
            "  t.fz_id,\n" +
            "  t.start_time,\n" +
            "  t.end_time,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as zy_label,\n" +
            "  c.label as fz_label \n" +
            "  FROM t_rmsg t " +
            "  LEFT JOIN t_zy b on t.zy_id = b.id " +
            "  LEFT JOIN t_fz c on t.fz_id = c.id " +
            "  WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TRmsg> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.zy_id,\n" +
            "  t.fz_id,\n" +
            "  t.start_time,\n" +
            "  t.end_time,\n" +
            "  t.create_time,\n" +
            "  t.update_time " +
            "  FROM t_rmsg t, t_sb b " +
            "  WHERE t.is_deleted = 0 AND t.fz_id = b.fz_id " +
            "  AND b.label = #{name} AND t.end_time > now() LIMIT 1")
    TRmsg selectMyValid(String name);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.zy_id,\n" +
            "  t.fz_id,\n" +
            "  t.start_time,\n" +
            "  t.end_time,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as zy_label,\n" +
            "  c.label as fz_label \n" +
            "  FROM t_rmsg t " +
            "  LEFT JOIN t_zy b on t.zy_id = b.id " +
            "  LEFT JOIN t_fz c on t.fz_id = c.id " +
            "  WHERE t.is_deleted = 0 AND t.id = #{id} LIMIT 1")
    TRmsg findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.zy_id,\n" +
            "  t.fz_id,\n" +
            "  t.start_time,\n" +
            "  t.end_time,\n" +
            "  t.create_time,\n" +
            "  t.update_time,\n" +
            "  b.label as zy_label,\n" +
            "  c.label as fz_label \n" +
            "  FROM t_rmsg t " +
            "  LEFT JOIN t_zy b on t.zy_id = b.id " +
            "  LEFT JOIN t_fz c on t.fz_id = c.id " +
            "  WHERE t.is_deleted = 0 AND t.label = #{label} LIMIT 1")
    TRmsg findOneByName(String label);

    @Insert("INSERT INTO t_rmsg(label, zy_id, fz_id, start_time, end_time) " +
            "VALUES(#{label}, #{zyId}, #{fzId}, #{startTime}, #{endTime})")
    int insert(TRmsg rmsg);

    @Update("update t_rmsg t set t.zy_id = #{zyId}, t.fz_id = #{fzId}, " +
            "t.start_time = #{startTime}, t.end_time = #{endTime} where t.id = #{id}")
    int updateByPrimaryKeySelective(TRmsg rmsg);

    @Delete("update t_rmsg t set t.is_deleted = 1 where t.id = #{id}")
    int deleteById(Integer id);

}
