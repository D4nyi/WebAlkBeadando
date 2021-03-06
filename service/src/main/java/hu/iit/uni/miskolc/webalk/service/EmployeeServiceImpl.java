package hu.iit.uni.miskolc.webalk.service;

import hu.iit.uni.miskolc.webalk.core.model.Employee;
import hu.iit.uni.miskolc.webalk.core.service.EmployeeService;
import hu.iit.uni.miskolc.webalk.core.service.exceptions.ExistingProblemException;
import hu.iit.uni.miskolc.webalk.core.service.exceptions.PersistenceException;
import hu.iit.uni.miskolc.webalk.core.service.exceptions.StorageProblemException;
import hu.iit.uni.miskolc.webalk.service.dao.EmployeeDAO;
import hu.iit.uni.miskolc.webalk.service.dao.exceptions.AlreadyExistException;
import hu.iit.uni.miskolc.webalk.service.dao.exceptions.NotFoundException;
import hu.iit.uni.miskolc.webalk.service.dao.exceptions.StorageException;
import hu.iit.uni.miskolc.webalk.service.dao.exceptions.StorageNotAvailableException;

import java.util.Collection;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public void createEmployee(Employee employee) throws StorageProblemException, ExistingProblemException, PersistenceException {
        try {
            employeeDAO.createEmployee(employee);
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (AlreadyExistException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public Employee getEmployee(int idNum) throws PersistenceException, ExistingProblemException, StorageProblemException {
        try {
            return employeeDAO.getEmployee(idNum);
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (NotFoundException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public Collection<Employee> getAllEmployee() throws PersistenceException, ExistingProblemException, StorageProblemException {
        try {
            return employeeDAO.getAllEmployee();
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (NotFoundException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public Collection<Employee> getEmployeeByShopName(String shopName) throws PersistenceException, ExistingProblemException, StorageProblemException {
        try {
            return employeeDAO.getEmployeeByShopName(shopName);
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (NotFoundException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public boolean updateEmployee(Employee employee) throws StorageProblemException, ExistingProblemException, PersistenceException {
        try {
            return employeeDAO.updateEmployee(employee);
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (NotFoundException | AlreadyExistException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public boolean deleteEmployee(int idNum) throws StorageProblemException, ExistingProblemException, PersistenceException {
        try {
            return employeeDAO.deleteEmployee(idNum);
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (NotFoundException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    public boolean deleteEmployee(Employee employee) throws StorageProblemException, ExistingProblemException, PersistenceException {
        try {
            return employeeDAO.deleteEmployee(employee);
        } catch (StorageNotAvailableException | StorageException e) {
            throw new StorageProblemException(e);
        } catch (NotFoundException e) {
            throw new ExistingProblemException(e);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
