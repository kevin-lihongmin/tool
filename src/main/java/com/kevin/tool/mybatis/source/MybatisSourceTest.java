package com.kevin.tool.mybatis.source;

import com.kevin.tool.mybatis.source.dao.UserMapper;
import com.kevin.tool.mybatis.source.entity.User;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 *  Mybatis源码解析
 *
 * @author lihongmin
 * @date 2020/7/9 13:24
 * @since 1.0.0
 */
public class MybatisSourceTest {

    @SneakyThrows
    public static void main(String[] args) {
        //
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        //
        SqlSession sqlSession = factory.openSession();
        //这里不再调用SqlSession 的api，而是获得了接口对象，调用接口中的方法。
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //
        User user = mapper.selectByPrimaryKey(1);
    }
}
