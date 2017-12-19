package cn.geekview.entity.mapper;

import cn.geekview.entity.model.TdreamUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TdreamUserMapper {

    /**
     *插入一条记录
     */
    String insert = "insert into t_dream_user (pk_id, email, nick_name, \n" +
            "      password, openid, gender, \n" +
            "      register_time, user_status, remark\n" +
            "      )\n" +
            "    values (#{pkId,jdbcType=INTEGER}, #{email,jdbcType=VARCHAR}, #{nickName,jdbcType=VARCHAR}, \n" +
            "      #{password,jdbcType=VARCHAR}, #{openid,jdbcType=VARCHAR}, #{gender,jdbcType=INTEGER}, \n" +
            "      #{registerTime,jdbcType=TIMESTAMP}, #{userStatus,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}\n" +
            "      )";
    @Insert(insert)
    int insert(TdreamUser user);

    /**
     * 根据邮箱和密码查询,并且用户状态是可用的
     */
    String queryByEamilAndPassword = "SELECT email from t_dream_user t where " +
            "     t.email = #{email,jdbcType=VARCHAR} " +
            "      and t.`password` = #{password,jdbcType=VARCHAR}" +
            "     and  t.user_status = 1";

    @Select(queryByEamilAndPassword)
    String queryByEamilAndPassword(TdreamUser user);

    /**
     * 判断邮箱是否已经注册
     */
    String emailIsExist = "SELECT email from t_dream_user t where " +
            "     t.email = #{email,jdbcType=VARCHAR}";
    @Select(emailIsExist)
    String emailIsExist(TdreamUser user);

}
