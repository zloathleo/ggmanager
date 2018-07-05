package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TZyxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ZyMapper extends Mapper<TZyxx>, MySqlMapper<TZyxx> {

    @Select("SELECT COUNT(1) FROM t_zy WHERE is_deleted = 0")
    int getCount();

    @Select("SELECT COUNT(1) FROM t_zy WHERE is_deleted = 0 AND type = #{type}")
    int getTypeCount(String type);

    @Select("SELECT COUNT(1) FROM t_zy AND type = #{type}")
    int getAllCount(String type);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.url,\n" +
            "  t.preview,\n" +
            "  t.size,\n" +
            "  t.height,\n" +
            "  t.width,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0" +
            "  ORDER BY t.create_time DESC ")
    List<TZyxx> selectAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.url,\n" +
            "  t.preview,\n" +
            "  t.size,\n" +
            "  t.height,\n" +
            "  t.width,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 AND t.type = #{type}" +
            "  ORDER BY t.create_time DESC ")
    List<TZyxx> selectTypeAll(String type);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.url,\n" +
            "  t.preview,\n" +
            "  t.size,\n" +
            "  t.height,\n" +
            "  t.width,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0" +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TZyxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.url,\n" +
            "  t.preview,\n" +
            "  t.size,\n" +
            "  t.height,\n" +
            "  t.width,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 AND type = #{type}" +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TZyxx> selectTypePage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.url,\n" +
            "  t.preview,\n" +
            "  t.size,\n" +
            "  t.height,\n" +
            "  t.width,\n" +
            "  t.des,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 " +
            "  AND t.label = #{label}")
    TZyxx selectByName(String label);

    @Insert("INSERT INTO t_zy(label, url, preview, size, height, width, des, type) " +
            "VALUES(#{label}, #{url}, #{preview}, #{size}, #{height}, #{width}, #{des}, #{type})")
    int insert(TZyxx zyxx);

    @Update("UPDATE t_zy t SET t.des = #{des}, t.update_time=NOW() WHERE t.label = #{label} AND t.is_deleted = 0")
    int updateByName(TZyxx zyxx);

    @Delete("update t_zy t set t.is_deleted = 1 where t.label = #{label}")
    int deleteByName(String label);
}
