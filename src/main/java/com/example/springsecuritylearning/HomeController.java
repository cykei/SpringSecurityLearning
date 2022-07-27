package com.example.springsecuritylearning;

import lombok.RequiredArgsConstructor;
import javax.naming.NamingException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final LdapTemplate ldapTemplate;

    @GetMapping("/")
    public String index(){
        return "hello world!";
    }

    @GetMapping("/design")
    public String design() {return "design";}

    @GetMapping("/search")
    public List<String> search() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });

        /*
        결과

        ["quote\\\"guy","Joe Smeth","Mouse, Jerry","slash/guy","Ben Alex","Bob Hamilton","Space Cadet"]
         */
    }
}
