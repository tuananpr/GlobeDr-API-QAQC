package stepdefinition;

import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.business.article.*;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.step.article.PostCategoryMS;
import com.rest.core.response.Response;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class ForumManageStep extends Data {

    AbsArticleBus manageBus = new ArticleManageBus();
    AbsArticleBus approveBus = new ArticleApproveBus();
    ArticleSystemBus systemBus = new ArticleSystemBus();
    PostCategoryBus postCategoryBus = new PostCategoryManage();

    @When("^I re-create a article category with below info$")
    public void recreateCategory(List<PostCategoryMS> list) {
        list.forEach(postCategoryBus::create);
    }

    @When("^I update below information for article category \"([^\"]*)\", language is (\\d+)$")
    public void updateCategory(String name, Integer language, List<PostCategoryMS> list) {
        PostCategoryMS oldCategory = new PostCategoryMS();
        oldCategory.setCategoryName(name);
        oldCategory.setLanguage(language);
        PostCategoryMS newData = list.get(0);
        postCategoryBus.updateTo(oldCategory, newData);
    }


    @Given("^Manager load article categories with below info$")
    public void managerGetAllCategories(List<PostCategoryMS> list) {
        list.forEach(c -> {
            c.setOrgSignature(orgSig);
            postCategoryBus.loads(c);
        });
    }

    @Given("^Approver load article categories with below info$")
    public void approverGetAllCategories(List<PostCategoryMS> list) {
        list.forEach(postCategoryBus::loads);
    }


    @Given("^Manager update below information for article title \"([^\"]*)\", language is (\\d+)$")
    public void updateArticles(String subject, Integer language, List<ArticleMS> articles) {
        ArticleMS newData = articles.get(0);
        ArticleMS oldPost = new ArticleMS();
        oldPost.setSubject(subject);
        oldPost.setLanguage(language);
        oldPost.setOrgSig(orgSig);
        manageBus.updateTo(oldPost, newData);
    }


    @Given("^Manager load notes article with below info$")
    public void managerLoadNotesArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach(p -> {
            p.setOrgSig(orgSig);
            manageBus.loadNotes(p);
        });
    }


    @Given("^Approver load notes article with below info$")
    public void approverLoadNotesArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach(p -> {
            approveBus.loadNotes(p);
        });
    }

    @Given("^Manager load log of rejected article with below info$")
    public void managerLoadRejectLogArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach(p -> {
            p.setOrgSig(orgSig);
            manageBus.loadRejectLog(p);
        });

    }

    @Given("^Approver reject article with below info$")
    public void approverRejectArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach(approveBus::reject);
    }

    @When("^Manager delete article with below info$")
    public void managerDeleteArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach(p -> {
            p.setOrgSig(orgSig);
            manageBus.delete(p);
        });
    }


    @Given("^(Manager|Approver) load details article with below info$")
    public void managerLoadDetailArticle(String user, List<ArticleMS> list) {
        list.forEach(manageBus::loadsDetail);
    }


    @Given("^Approver approve article with below info$")
    public void managerLoadDetailArticle(List<ArticleMS> list) {
        list.forEach(approveBus::approve);
    }

    @Given("^Manager create article into category$")
    public void createArticle(List<ArticleMS> list) {
        list.forEach(article -> {
            article.setOrgSig(orgSig);
            Response rs = manageBus.create(article);
            if (response.isSuccess()) postSig = rs.extract("data.postSignature");
        });

    }

    @Then("I must see a below system post")
    public void iMustSeeABelowSystemPost(List<ArticleMS> list) {
        for (ArticleMS post : list) {
            List<ArticleMS> rs = systemBus.loads(post).extractAsModels("$.data.list[*].list[*]", ArticleMS.class);

            Assert.assertEquals(post.getSubject(), rs.get(0).getSubject());
            Assert.assertEquals(post.getMsg(), rs.get(0).getMsg());
            Assert.assertEquals(post.getType(), rs.get(0).getType());
            Assert.assertEquals(post.getKey(), rs.get(0).getKey());
        }
    }


    @Then("^I update system post that has \"([^\"]*)\" type and \"([^\"]*)\" subject with below info$")
    public void updateSystemPost(int type, String subject, List<ArticleMS> list) {
        ArticleMS newData = list.get(0);
        ArticleMS oldPost = new ArticleMS();
        oldPost.setType(type);
        oldPost.setSubject(subject);
        systemBus.updateTo(oldPost, newData);
    }


    @Then("^I add this \"([^\"]*)\" image into the above article$")
    public void addImageIntoPost(String path) {
        manageBus.updateForumIcon(orgSig, postSig, path);
    }


    @And("^I (turn on|turn off) config to show globedr articles on my organization$")
    public void iUseGlobedrArticlesForMyOrganization(String action) {
        ArticleMS post = new ArticleMS();
        post.setOrgSig(orgSig);
        manageBus.showGlobedrArticleConfig(post, action.equalsIgnoreCase("turn on"));
    }


    @And("I load config to show globedr articles on my organization")
    public void iloadConfigToShowGlobedrArticles() {
        ArticleMS post = new ArticleMS();
        post.setOrgSig(orgSig);
        manageBus.showGlobedrArticleValue(post);
    }


    @When("^Manager load article with below info$")
    public void managerLoadArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach( article ->{
            article.setOrgSig(orgSig);
            manageBus.loads(article);
        });
    }

    @When("^Approver load article with below info$")
    public void approveLoadArticleWithBelowInfo(List<ArticleMS> list) {
        list.forEach(approveBus::loads);
    }


    @When("^I create a new system post$")
    public void createPostSystem(List<ArticleMS> list) {
        list.forEach(systemBus::create);
    }


    @And("Manager submit article")
    public void managerSubmitArticle(List<ArticleMS> list) {
        list.forEach( article ->{
            article.setOrgSig(orgSig);
            manageBus.submitPost(article);
        });
    }
}
