package com.cdfive.demo.mybatis.typehandler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cdfive
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractEnumSetNameTypeHandler<E extends Enum> extends BaseTypeHandler<Set<E>> {

    private final Class<E> clazz;

    public AbstractEnumSetNameTypeHandler() {
        clazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set<E> objects, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isEmpty(objects)) {
            return;
        }


        Set<String> names = objects.stream().filter(Objects::nonNull).map(o -> o.name()).collect(Collectors.toCollection(LinkedHashSet::new));
        if (CollectionUtils.isEmpty(objects)) {
            return;
        }

        String value = String.join(",", names);
        ps.setString(i, value);
    }

    @Override
    public Set<E> getNullableResult(ResultSet rs, String s) throws SQLException {
        return parse(rs.getString(s));
    }

    @Override
    public Set<E> getNullableResult(ResultSet rs, int i) throws SQLException {
        return parse(rs.getString(i));
    }

    @Override
    public Set<E> getNullableResult(CallableStatement cs, int i) throws SQLException {
        return parse(cs.getString(i));
    }

    private Set<E> parse(String value) {
        if (StringUtils.isBlank(value)) {
            return new LinkedHashSet<>();
        }

        Iterator<String> iterator = Arrays.stream(value.split(",")).iterator();
        Set<E> result = new LinkedHashSet<>();
        while (iterator.hasNext()) {
            try {
                E e = (E) Enum.valueOf(clazz, iterator.next());
                result.add(e);
            } catch (Exception e) {
                // Ignore
            }
        }
        return result;
    }
}
