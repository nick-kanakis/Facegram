package gr.personal.story.repository;

import gr.personal.story.domain.Comment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Nick Kanakis on 17/5/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Before
    public void setUp(){
        Comment comment = new Comment();
        comment.setHeader("testHeader");
        comment.setPostDate(new Date());
        comment.setUserId("testUserId");
        comment.setDescription("testDesc");

        Assert.assertNull(comment.getId());
        this.commentRepository.save(comment);
        Assert.assertNotNull(comment.getId());

    }
    @Test
    public void shouldFetchData(){
        List<Comment> comments = commentRepository.findByUserId("testUserId");
        Assert.assertNotNull(comments);
        Assert.assertEquals("testHeader", comments.get(0).getHeader());

    }

    @Test
    public void shouldUpdateDate(){

        List<Comment> comments = commentRepository.findByUserId("testUserId");
        comments.get(0).setDescription("testDesc2");
        commentRepository.save(comments);
        List<Comment> commentsAfterUpdate = commentRepository.findByUserId("testUserId");
        Assert.assertEquals("testDesc2", commentsAfterUpdate.get(0).getDescription());


    }

    @After
    public void tearDown() throws Exception{
        this.commentRepository.deleteAll();
    }
}
