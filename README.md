# Twitter clone in Spring boot
Twitter clone that covers the core features of twitter


## Usage
1. Clone the repo or download it.
    ```
   git clone https://github.com/fabiodelabruna/instagram-clone.git
    ```
   
3. Run following command for build application.
   ```
    ./mvnw clean package
    ```
2. Then use following command to run application using docker compose.
    ```
    docker-compose up --build
    ```



## Documentation
After running application, documentation will be available at http://localhost:8080/swagger-ui.html 


## Todo





Auth
- [x] User can create an account using email and password.
- [x]    User must verify him/his email after create account
- [x]    User can log in using email, password or simply do login using google authentication.
- [x]   User can send reset password link to his email from forgot password page.

Tweet
- [x] The user can list the tweets of people they follow on their homepage ordered by date
- [x] The user can reply, like, retweet and quote tweet by tapping on comment Icon.
- [x] The user can list the people who liked, retweeted, quoted the tweet
- [x] The user can list the replies of tweet


