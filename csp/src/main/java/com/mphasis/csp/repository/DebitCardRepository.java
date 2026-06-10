package com.mphasis.csp.repository;

import com.mphasis.csp.model.DebitCard;
import com.mphasis.csp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, String> {


    List<DebitCard> findByUser(User user);

}