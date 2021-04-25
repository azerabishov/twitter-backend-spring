package org.azerabshv.repository.tweet;

import org.azerabshv.models.Tweet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByReplyTo(Long id, Pageable pageable);
    List<Tweet> findByQuoteTo(Long id, Pageable pageable);
    @Query(value="select * from tweets left join  users on tweets.user_id = users.user_id where users.user_id in (:userIds)", nativeQuery = true)
    List<Tweet> getAllByUserIds(List<Long> userIds);
    @Query(value="select * from tweets  where tweets.user_id = :userId limit=:limit offset=:offset", nativeQuery = true)
    List<Tweet> findByUser(long userId, int limit, int offset);
    @Query(value = "select * from tweets join likes on likes.tweet_id = tweets.tweet_id where likes.user_id=:userId limit=:limit offset=:offset", nativeQuery = true)
    List<Tweet> findTweetsByUserLikes(long userId, int limit,  int offset);
    @Query(value = "select * from tweets  where tweets.user_id=:userId and tweets.tweet_type=:tweetType limit=:limit offset=:offset", nativeQuery = true)
    List<Tweet> findUserTweetByType(long userId, int limit, int offset, String tweetType);
    @Query(value = "select * from tweets join likes on bookmarks.tweet_id = tweets.tweet_id where bookmarks.user_id=:userId limit=:limit offset=:offset", nativeQuery = true)
    List<Tweet> findTweetsFromBookmarks(long userId, int limit, int offset);
}


