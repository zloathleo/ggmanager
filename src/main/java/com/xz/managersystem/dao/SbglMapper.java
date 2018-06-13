package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TDevice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface SbglMapper extends Mapper<TDevice>, MySqlMapper<TDevice> {

    @Select("SELECT COUNT(1) FROM t_device WHERE is_delete = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_device")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.ggym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_device t WHERE is_delete = 0 ORDER BY create_time DESC LIMIT #{start},#{rows} ")
    List<TDevice> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.ggym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_device t WHERE t.id =#{id} LIMIT 1")
    TDevice findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.location,\n" +
            "  t.ggym_id,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_device t  WHERE t.label =#{label} LIMIT 1")
    TDevice findOneByName(String label);

    @Insert("INSERT INTO t_device (label,des,location, ggym_id) VALUES ( #{label},#{des},#{location}, #{ggymId} )")
    int insert(TDevice device);

    @Update("update t_device t set t.des=#{des},t.location=#{location},t.ggym_id=#{ggymId}  where t.id=#{id}")
    int updateByPrimaryKeySelective(TDevice device);

    @Delete("update t_device t set t.is_delete=1 where t.id=#{id}")
    int deleteById(Integer id);

}
