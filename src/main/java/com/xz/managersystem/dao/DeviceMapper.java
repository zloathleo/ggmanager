package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TDeviceInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface DeviceMapper extends Mapper<TDeviceInfo>, MySqlMapper<TDeviceInfo> {

    @Select("SELECT COUNT(1) FROM t_device WHERE is_deleted = 0 AND `group` = #{group}")
    int getCount(String group);

    @Select("SELECT * from t_device WHERE is_deleted = 0 AND `group` = #{group} ORDER BY create_time DESC")
    List<TDeviceInfo> selectList(String group);

    @Select("SELECT * from t_device WHERE is_deleted = 0 AND `group` = #{group} " +
            "ORDER BY create_time DESC LIMIT #{start}, #{rows}")
    List<TDeviceInfo> selectPage(ConditionParams params);

    @Select("SELECT * from t_device WHERE is_deleted = 0 AND id = #{id} LIMIT 1")
    TDeviceInfo selectById(Integer id);

    @Select("SELECT * FROM t_device WHERE is_deleted = 0 AND label = #{label} LIMIT 1")
    TDeviceInfo selectByLabel(String label);

    @Insert("INSERT INTO t_device(label, name, brand, model, `group`) " +
            "VALUES(#{label}, #{name}, #{brand}, #{model}, #{group})")
    int insert(TDeviceInfo pageInfo);

    @Update("UPDATE t_device SET name = #{name}, brand = #{brand}, model = #{model}, " +
            "update_time = NOW() WHERE id = #{id}")
    int updateById(TDeviceInfo pageInfo);

    @Update("UPDATE t_device SET name = #{name}, brand = #{brand}, model = #{model}, " +
            "update_time = NOW() WHERE label = #{label}")
    int updateByLabel(TDeviceInfo pageInfo);

    @Delete("UPDATE t_device SET is_deleted = 1 WHERE id = #{id}")
    int deleteById(Integer id);

    @Delete("UPDATE t_device SET is_deleted = 1 WHERE label = #{label}")
    int deleteByLabel(String label);

}
