package cn.geekview.entity.mapper;

import cn.geekview.entity.model.TdreamUser;
import org.apache.ibatis.annotations.*;

import java.util.Date;

public interface TdreamUserMapper {

    /**
     *插入一条记录
     */
    String insert = "insert into t_dream_user (pk_id, email, nick_name, \n" +
            "      password, openid, gender, \n" +
            "      register_time, user_status, active_code, \n" +
            "      exprie_time, remark)\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{email,jdbcType=VARCHAR}, #{nickName,jdbcType=VARCHAR}, \n" +
            "      #{password,jdbcType=VARCHAR}, #{openid,jdbcType=VARCHAR}, #{gender,jdbcType=INTEGER}, \n" +
            "      #{registerTime,jdbcType=TIMESTAMP}, #{userStatus,jdbcType=INTEGER}, #{activeCode,jdbcType=VARCHAR}, \n" +
            "      #{exprieTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=VARCHAR})";
    @Insert(insert)
    @Options(useGeneratedKeys = true,keyProperty = "pkId")
    Integer insert(TdreamUser user);

    /**
     * 根据邮箱和密码查询,并且用户状态是可用的
     */
    String queryByEamilAndPassword = "SELECT email from t_dream_user t where " +
            "     t.email = #{email,jdbcType=VARCHAR} " +
            "      and t.`password` = #{password,jdbcType=VARCHAR}" ;

    @Select(queryByEamilAndPassword)
    String queryByEamilAndPassword(TdreamUser user);

    /**
     * 判断邮箱是否已经注册
     */
    String emailIsExist = "SELECT email from t_dream_user t where " +
            "     t.email = #{email,jdbcType=VARCHAR}";
    @Select(emailIsExist)
    String emailIsExist(TdreamUser user);


    /**
     * 邮箱激活
     */
    String active_email = " update t_dream_user\n" +
            "    set user_status = #{userStatus,jdbcType=INTEGER}\n" +
            "    where email = #{email,jdbcType=VARCHAR}";
    @Update(active_email)
    void active_email(TdreamUser user);

    /**
     * 根据邮箱和激活码查询
     */
    String queryByEmailAndActiveCode = "SELECT email from t_dream_user t where " +
            "     t.email = #{email,jdbcType=VARCHAR} " +
            "      and t.active_code = #{activeCode,jdbcType=VARCHAR}" +
            "       and t.exprieTime > #{activeTime,jdbcType=TIMESTAMP}" ;
    @Select(queryByEmailAndActiveCode)
    String queryByEmailAndActiveCode(TdreamUser user, @Param("activeTime")Date activeTime);

    /**
     * 根据邮箱和激活码删除
     */
    String deleteByEmailAndActiveCode = "delete from t_dream_user where " +
            "   t.email = #{email,jdbcType=VARCHAR} " +
            "   and t.active_code = #{activeCode,jdbcType=VARCHAR}";
    void deleteByEmailAndActiveCode(TdreamUser user);
}
