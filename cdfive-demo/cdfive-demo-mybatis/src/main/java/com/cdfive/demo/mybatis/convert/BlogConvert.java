package com.cdfive.demo.mybatis.convert;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author cdfive
 */
@SuppressWarnings("unused")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, builder = @Builder(disableBuilder = true))
public interface BlogConvert {

    BlogConvert INSTANCE = Mappers.getMapper(BlogConvert.class);
}
