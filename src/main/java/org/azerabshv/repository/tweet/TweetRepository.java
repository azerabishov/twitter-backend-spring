package org.azerabshv.repository.tweet;

import org.azerabshv.models.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByReplyTo(Long id);

    List<Tweet> findByQuoteTo(Long id);

    @Query(value="select * from tweets left join  users on tweets.user_id = users.user_id where users.user_id in (:userIds)", nativeQuery = true)
    List<Tweet> getAllByUserIds(List<Long> userIds);

    @Query(value = "select * from tweets  where tweets.user_id=:userId and tweets.tweet_type=:tweetType", nativeQuery = true)
    List<Tweet> findUserTweetByType(long userId, String tweetType);

    @Query("SELECT t FROM Tweet t WHERE t.content LIKE CONCAT('%',:searchKey,'%')")
    List<Tweet> findTweetByContent(String searchKey);

}