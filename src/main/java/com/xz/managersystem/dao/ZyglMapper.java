package com.xz.managersystem.dao;

import com.xz.managersystem.entity.TZyxx;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface ZyglMapper extends Mapper<TZyxx>, MySqlMapper<TZyxx> {

    @Select("SELECT COUNT(1) FROM t_zy WHERE is_deleted = 0")
    int getVisibleCount();

    @Select("SELECT COUNT(1) FROM t_zy")
    int getAllCount();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.content,\n" +
            "  t.link,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 " +
            "  ORDER BY t.create_time DESC ")
    List<TZyxx> selectVisibleAll();

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.content,\n" +
            "  t.link,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 AND type = #{type}" +
            "  ORDER BY t.create_time DESC LIMIT #{start}, #{rows}")
    List<TZyxx> selectPage(TablePageParams params);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.content,\n" +
            "  t.link,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 and t.id = #{id} LIMIT 1")
    TZyxx findOne(Integer id);

    @Select("SELECT \n" +
            "  t.id,\n" +
            "  t.label,\n" +
            "  t.des,\n" +
            "  t.content,\n" +
            "  t.link,\n" +
            "  t.type,\n" +
            "  t.create_time,\n" +
            "  t.update_time \n" +
            "  FROM t_zy t WHERE t.is_deleted = 0 " +
            "  AND t.label = #{label} LIMIT 1")
    TZyxx findOneByName(String label);

    @Insert("INSERT INTO t_zy(label, des, content, link, type) VALUES(#{label}, #{des}, #{content}, #{link}, #{type})")
    int insert(TZyxx zyxx);

    @Update("update t_zy t set t.des = #{des}, t.content= #{content}, t.link = #{link} where t.id = #{id}")
    int updateByPrimaryKeySelective(TZyxx zyxx);

    @Delete("update t_zy t set t.is_deleted = 1 where t.id = #{id}")
    int deleteById(Integer id);

}
