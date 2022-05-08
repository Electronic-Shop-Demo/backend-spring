package com.mairwunnx.application.mapper;

import com.mairwunnx.application.entity.mongo.UserDocument;
import com.mairwunnx.application.user.SpringSecurityUser;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;

@Mapper
public interface SpringUserMapper {

    SpringUserMapper INSTANCE = Mappers.getMapper(SpringUserMapper.class);

    @ParametersAreNonnullByDefault
    @NotNull SpringSecurityUser entityToDto(final UserDocument entity);

    @Mapping(target = "avatar")
    @Mapping(target = "phone")
    @Mapping(target = "fullname")
    @Mapping(target = "creationDate")
    @Mapping(target = "lastVisitDate")
    @Mapping(target = "favorite")
    @Mapping(target = "cart")
    @Mapping(target = "order")
    @Mapping(target = "userSettings")
    @ParametersAreNonnullByDefault
    @NotNull UserDocument dtoToEntity(final SpringSecurityUser entity);

    @ParametersAreNonnullByDefault
    default @NotNull Collection<? extends GrantedAuthority> map(final List<String> value) {
        return value.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @ParametersAreNonnullByDefault
    default @NotNull List<String> map(final Collection<? extends GrantedAuthority> value) {
        return value.stream().map(GrantedAuthority::getAuthority).toList();
    }

}
