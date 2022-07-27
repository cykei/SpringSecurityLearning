package com.example.springsecuritylearning;

import lombok.RequiredArgsConstructor;

import javax.naming.NamingException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.Attributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final LdapTemplate ldapTemplate;

    @GetMapping("/")
    public String index() {
        return "hello world!";
    }

    @GetMapping("/design")
    public String design() {
        return "design";
    }

    @GetMapping("/search")
    public List<String> search() {

        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "groupOfUniqueNames"));
        filter.and(new EqualsFilter("cn", "developers"));

        List<Object> results = ldapTemplate.search(
                "", filter.encode(),
                new AttributesMapper() {
                    public Object mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        List aa = Collections.list(attrs.get("uniqueMember").getAll());
                        for (Object a : aa) {
                            log.info("a: " + (String) a);
                        }
                        return aa;
                    }
                });

        ArrayList<String> arrayList = (ArrayList<String>) results.get(0);
        return arrayList;

        /* 아래 코드는 ben 하나만 가져옴 */
//       List<String> results =  ldapTemplate.search(
//                "", filter.encode(),
//                new AttributesMapper() {
//                    public Object mapFromAttributes(Attributes attrs)
//                            throws NamingException {
//                        return attrs.get("uniqueMember").get();
//                    }
//                });

        /* 아래 코드는 모든 groupOfUniqueNames 에서 가장 위에 있는 uniqueMember만 출력함. */

//        return ldapTemplate.search(
//                query().where("objectclass").is("groupOfUniqueNames"),
//                new AttributesMapper() {
//                    public String mapFromAttributes(Attributes attrs)
//                            throws NamingException {
//                        return (String) attrs.get("uniqueMember").get();
//                    }
//                });

        /*
        결과

       ["uid=ben,ou=people,dc=springframework,dc=org","uid=ben,ou=people,dc=springframework,dc=org","uid=ben,ou=people,dc=springframework,dc=org"]

        */
    }
}
