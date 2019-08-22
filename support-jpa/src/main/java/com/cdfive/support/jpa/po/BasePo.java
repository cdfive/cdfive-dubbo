package com.cdfive.support.jpa.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author cdfive
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BasePo<T extends Serializable> implements Serializable {

    @Id
    private T id;

    private Timestamp createTime;

    @UpdateTimestamp
    private Timestamp updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof BasePo)) {
            return false;
        }

        BasePo that = (BasePo) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "#" + this.getId();
    }
}
