package rs.raf.demo;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.demo.repositories.category.CategoryRepository;
import rs.raf.demo.repositories.category.MySqlCategoryRepository;
import rs.raf.demo.repositories.newstag.comment.CommentRepository;
import rs.raf.demo.repositories.newstag.comment.MySqlCommentRepository;
import rs.raf.demo.repositories.news.MySqlNewsRepository;
import rs.raf.demo.repositories.news.NewsRepository;
import rs.raf.demo.repositories.newstag.MySqlNewsTagRepository;
import rs.raf.demo.repositories.newstag.NewsTagRepository;
import rs.raf.demo.repositories.tag.MySqlTagRepository;
import rs.raf.demo.repositories.tag.TagRepository;
import rs.raf.demo.repositories.user.MySqlUsersRepository;
import rs.raf.demo.repositories.user.UserRepository;
import rs.raf.demo.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {

    public HelloApplication() {
        // Ukljucujemo validaciju
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        // Definisemo implementacije u dependency container-u
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlCategoryRepository.class).to(CategoryRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(TagRepository.class).in(Singleton.class);
                this.bind(MySqlNewsRepository.class).to(NewsRepository.class).in(Singleton.class);
                this.bind(MySqlCommentRepository.class).to(CommentRepository.class).in(Singleton.class);
                this.bind(MySqlNewsTagRepository.class).to(NewsTagRepository.class).in(Singleton.class);

                this.bind(MySqlUsersRepository.class).to(UserRepository.class).in(Singleton.class);

                this.bindAsContract(CategoryService.class);
                this.bindAsContract(TagService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(CommentService.class);
                this.bindAsContract(NewsTagService.class);

                this.bindAsContract(UserService.class);

            }
        };
        register(binder);

        // Ucitavamo resurse
        packages("rs.raf.demo");
    }
}
