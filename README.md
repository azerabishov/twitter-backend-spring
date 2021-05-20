# Twitter clone in Spring boot
Twitter clone that covers the core features of twitter


## Usage
1. Clone the repo or download it.
    ```
   git clone https://github.com/fabiodelabruna/instagram-clone.git
    ```
2. Enter your own credentials in application.properties file.

  
3. Run following command for build application.
   ```
    ./mvnw clean package
    ```
4. Then use following command to run application using docker compose.
    ```
    docker-compose up --build
    ```



## Documentation
After running application, documentation will be available at http://localhost:9090/swagger-ui.html 


## Todo

Auth
- [x] User can create an account using email and password.
- [x]    User must verify him/his email after create account
- [x]   User can send reset password link to his email.

User
- [x] Users can update his profile information.
- [x] Users can update his password.
- [x] Users can list their tweets as all tweets, medias, likes.

Tweet
- [x] The user can list the tweets of people they follow on their homepage ordered by date
- [x] The user can reply, like, retweet and quote tweet by tapping on comment Icon.
- [x] The user can list the people who liked, retweeted, quoted the tweet
- [x] The user can list the replies of tweet
- [x] The user can search tweets and other profiles.
- [x] The user can lock his/her profile, as a result other users cannot view his tweets (only users following it).


