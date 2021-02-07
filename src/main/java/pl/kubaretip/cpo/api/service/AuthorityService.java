package pl.kubaretip.cpo.api.service;

import pl.kubaretip.cpo.api.domain.Authority;

public interface AuthorityService {
    Authority getAuthority(String role);
}
