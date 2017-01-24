package pl.pvkk.profit.user.verification;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.pvkk.profit.user.User;

public interface VerificationTokenRepository 
extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);
}
