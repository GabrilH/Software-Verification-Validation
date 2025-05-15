package webapp.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import webapp.persistence.PersistenceException;
import webapp.persistence.SaleDeliveryRowDataGateway;
import webapp.persistence.SaleRowDataGateway;
import webapp.persistence.SaleStatus;

public class SaleServiceImpl implements ISaleService {
    private static SaleServiceImpl instance;
    private final ICustomerService customerService;
    
    // Static factory method for normal application use
    public static SaleServiceImpl getInstance() {
        if (instance == null) {
            instance = new SaleServiceImpl(CustomerServiceImpl.getInstance());
        }
        return instance;
    }
    
    // Constructor with dependency injection - used for testing and DI
    public SaleServiceImpl(ICustomerService customerService) {
        this.customerService = customerService;
    }
    
    @Override
    public SalesDTO getSaleByCustomerVat(int vat) throws ApplicationException {
        if (!customerService.isValidVAT(vat)) {
            throw new ApplicationException("Invalid VAT number: " + vat);
        }
        
        try {
            List<SaleRowDataGateway> sales = new SaleRowDataGateway().getAllSales(vat);
            List<SaleDTO> list = new ArrayList<>();
            for (SaleRowDataGateway sl : sales) {
                list.add(new SaleDTO(sl.getId(), sl.getData(), sl.getTotal(), 
                                    sl.getStatusId(), sl.getCustomerVat()));
            }
            return new SalesDTO(list);
        } catch (PersistenceException e) {
            throw new ApplicationException("Customer with vat number " + vat + " not found.", e);
        }
    }
    
    @Override
    public SalesDTO getAllSales() throws ApplicationException {
        try {
            List<SaleRowDataGateway> sales = new SaleRowDataGateway().getAllSales();
            List<SaleDTO> list = new ArrayList<>();
            for (SaleRowDataGateway sl : sales) {
                list.add(new SaleDTO(sl.getId(), sl.getData(), sl.getTotal(), 
                                    sl.getStatusId(), sl.getCustomerVat()));
            }
            return new SalesDTO(list);
        } catch (PersistenceException e) {
            throw new ApplicationException("Error loading sales.", e);
        }
    }
    
    @Override
    public void addSale(int customerVat) throws ApplicationException {
        // Verify customer exists through CustomerService
        try {
            customerService.getCustomerByVat(customerVat);
            
            // Only create sale if customer exists
            SaleRowDataGateway sale = new SaleRowDataGateway(customerVat, new Date());
            sale.insert();
        } catch (ApplicationException e) {
            throw new ApplicationException("Cannot add sale: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new ApplicationException("Cannot add sale for customer with VAT " + customerVat, e);
        }
    }
    
    @Override
    public void updateSale(int id) throws ApplicationException {
        try {
            SaleRowDataGateway sale = new SaleRowDataGateway().getSaleById(id);
            sale.setSaleStatus(SaleStatus.CLOSED);
            sale.updateSale();
        } catch (PersistenceException e) {
            throw new ApplicationException("Sale with id " + id + " doesn't exist.", e);
        }
    }
    
    @Override
    public SalesDeliveryDTO getSalesDeliveryByVat(int vat) throws ApplicationException {
        // Verify VAT is valid
        if (!customerService.isValidVAT(vat)) {
            throw new ApplicationException("Invalid VAT number: " + vat);
        }
        
        try {
            List<SaleDeliveryRowDataGateway> salesd = new SaleDeliveryRowDataGateway().getAllSaleDelivery(vat);
            List<SaleDeliveryDTO> list = new ArrayList<>();
            for (SaleDeliveryRowDataGateway sd : salesd) {
                list.add(new SaleDeliveryDTO(sd.getId(), sd.getSale_id(), 
                                           sd.getCustomerVat(), sd.getAddr_id()));
            }
            return new SalesDeliveryDTO(list);
        } catch (PersistenceException e) {
            throw new ApplicationException("Customer with vat number " + vat + " not found.", e);
        }
    }
    
    @Override
    public int addSaleDelivery(int sale_id, int addr_id) throws ApplicationException {
        try {
            // Get the sale to find its customer
            SaleRowDataGateway s = new SaleRowDataGateway().getSaleById(sale_id);
            
            // Verify customer exists by checking VAT
            int customerVat = s.getCustomerVat();
            customerService.getCustomerByVat(customerVat);
            
            // Create the delivery
            SaleDeliveryRowDataGateway sale = new SaleDeliveryRowDataGateway(sale_id, customerVat, addr_id);
            sale.insert();
            return sale.getCustomerVat();
        } catch (ApplicationException e) {
            throw new ApplicationException("Cannot add delivery: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            throw new ApplicationException("Cannot add delivery to sale", e);
        }
    }

    /**
	 * Checks if a VAT number is valid.
	 * 
	 * @param vat The number to be checked.
	 * @return Whether the VAT number is valid. 
	 */
	private boolean isValidVAT(int vat) {
		// If the number of digits is not 9, error!
		if (vat < 100000000 || vat > 999999999)
			return false;
		
		// If the first number is not 1, 2, 5, 6, 8, 9, error!
		int firstDigit = vat / 100000000;
		if (firstDigit != 1 && firstDigit != 2 && 
			firstDigit != 5 && firstDigit != 6 &&
			firstDigit != 8 && firstDigit != 9)
			return false;
		
		// Checks the congruence modules 11.
		int sum = 0;
		int checkDigit = vat % 10;
		vat /= 10;
		
		for (int i = 2; i < 10 && vat != 0; i++) {
			sum += vat % 10 * i;
			vat /= 10;
		}
		
		int checkDigitCalc = 11 - sum % 11;
		if (checkDigitCalc == 10)
			checkDigitCalc = 0;
		return checkDigit == checkDigitCalc;
	}
    
} 