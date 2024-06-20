package stepdefinition;

import com.apis.globedr.business.article.AbsArticleBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.business.article.ArticleGuestBus;
import com.apis.globedr.business.article.ArticleUserBus;
import com.apis.globedr.model.step.article.ArticleMS;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.List;

public class ForumStep extends Data {


    AbsArticleBus userBus = new ArticleUserBus();
    AbsArticleBus guestBus = new ArticleGuestBus();
    UserSearchBus userSearchBus = new UserSearchBus();

    @Given("^User load details article$")
    public void userLoadDetailsArticle(List<ArticleMS> list) {
        list.forEach(userBus::loadsDetail);
    }

    @Given("^Guest load details article$")
    public void guestLoadDetailsArticle(List<ArticleMS> list) {
        list.forEach(guestBus::loadsDetail);
    }


    @Given("^User load related article$")
    public void userLoadRelatedAboveArticle(List<ArticleMS> list) {
        list.forEach(userBus::loadRelatedPost);
    }


    @Given("^User load article at homepage$")
    public void userLoadArticleAtHomePage(List<ArticleMS> list) {
        list.forEach(userBus::loadCategoryWithPost);
    }


    @Given("^Guest load article at homepage$")
    public void guestLoadArticleAtHomePage(List<ArticleMS> list) {
        list.forEach(guestBus::loadCategoryWithPost);
    }


    @Given("^Guest load related above article$")
    public void guestLoadRelatedAboveArticle(List<ArticleMS> list) {
        list.forEach(guestBus::loadRelatedPost);
    }


    @Given("^User load comments above article$")
    public void userLoadCommentsAboveArticle(List<ArticleMS> list) {
        list.forEach(userBus::loadComment);
    }

    @Given("^Guest load comments above article$")
    public void guestLoadCommentsAboveArticle(List<ArticleMS> list) {
        list.forEach(guestBus::loadComment);
    }

    @Given("^User (like|unlike) article$")
    public void userLikeAndUnlikeAboveArticle(String action, List<ArticleMS> list) {
        if (action.equalsIgnoreCase("like")) {
            list.forEach(userBus::likePost);
        } else {
            list.forEach(userBus::unlikePost);
        }
    }

    @Given("^Guest (like|unlike) above article$")
    public void guestLikeAndUnlikeAboveArticle(String action, List<ArticleMS> list) {
        if (action.equalsIgnoreCase("like")) {
            list.forEach(guestBus::likePost);
        } else {
            list.forEach(guestBus::unlikePost);
        }
    }

    @Given("^(User|Guest) load articles$")
    public void guestLoadArticles(String user, List<ArticleMS> list) {
        list.forEach(article -> {
            if (article.getOrgName() != null){
                article.setOrgSig(userSearchBus.loadOrgsByName(article.getOrgName()).get(0).getSig());
            }
            userBus.loads(article);
        });
    }

    @Then("^User (like|unlike) comment$")
    public void likeCommentPost(String action, List<ArticleMS> list) {
        if (action.equalsIgnoreCase("like")) {
            list.forEach(userBus::likeComment);
        } else {
            list.forEach(userBus::likeComment);
        }
    }

    @Then("^I comment into article$")
    public void commentPost(List<ArticleMS> list) {
        list.forEach(userBus::comment);
    }

    @Then("^I send image to comment into article$")
    public void commentPostWithImage(List<ArticleMS> list) {
        list.forEach(userBus::addDocToComment);
    }


}
