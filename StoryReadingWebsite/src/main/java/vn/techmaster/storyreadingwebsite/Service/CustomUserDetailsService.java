package vn.techmaster.storyreadingwebsite.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.techmaster.storyreadingwebsite.entity.CustomUserDetails;
import vn.techmaster.storyreadingwebsite.entity.User;
import vn.techmaster.storyreadingwebsite.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository repo;

    //Phương thức này sẽ được gọi bởi Spring Security để lấy ra thông tin của tài khoản có username là username được nhập từ màn hình login.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy user");
        }

        return new CustomUserDetails(user);
    }

}
