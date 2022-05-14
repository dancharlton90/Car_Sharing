package carsharing;

import java.util.List;

public interface CompanyDao {
    List<Company> getAllCompanies();
    Company getCompany();
    void createCompany(String name);
    void updateCompany(Company company);
    void deleteCompany(Company company);
}
