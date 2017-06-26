package me.akuo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by pc on 2017/4/10.
 */
public interface HelloMapper {
    @Select("SELECT X FROM DUAL")
    String getUser(@Param("id") String userId);
}
