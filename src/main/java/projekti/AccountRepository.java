/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Pekka
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
    Account findByAlias(String alias);
    List<Account> findByFullNameIgnoreCaseContains(String fullName);
    List<Account> findByAliasIgnoreCaseContains(String alias);
}
