package com.bank.authorization.service;

import com.bank.authorization.model.UserEntity;
import com.bank.authorization.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Основной метод для загрузки пользователя по имени
     * Spring Security вызывается при попытке аутентификации
     *
     * @param username имя пользователя из формы логина
     * @return UserDetails объект с данными пользователя для Spring Security
     * @throws UsernameNotFoundException если пользователь не найден или отключен
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в базе данных
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Проверяем, активен ли пользователь
        if (!Boolean.TRUE.equals(user.getIsEnabled())) {
            throw new UsernameNotFoundException("User is disabled: " + username);
        }

        // Создаем authority (роль) для Spring Security
        // Преобразуем название роли в формат ROLE_НАЗВАНИЕ
        String roleName = user.getRole().getRoleName().toUpperCase();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
        List<GrantedAuthority> authorities = Collections.singletonList(authority);

        // Создаем объект User Spring Security
        return new User(
                user.getUsername(),           // имя пользователя
                user.getPasswordHash(),       // хэш пароля (Spring Security сравнит с введенным)
                true,                         // enabled - аккаунт активен
                true,                         // accountNonExpired - аккаунт не просрочен
                true,                         // credentialsNonExpired - пароль не просрочен
                true,                         // accountNonLocked - аккаунт не заблокирован
                authorities                   // список ролей/прав
        );
    }
}
