package com.automobile.notification.dealer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automobile.notification.dealer.dao.DealerDAO;
import com.automobile.notification.dealer.dao.VehicleModelDAO;
import com.automobile.notification.dealer.domain.DealerEntity;
import com.automobile.notification.dealer.domain.VehicleModelEntity;
import com.automobile.notification.dealer.exception.DealerException;
import com.automobile.notification.dealer.exception.VehicleModelException;
import com.automobile.notification.dealer.validator.DealerValidator;
import com.automobile.notification.dealer.validator.VehicleModelValidator;
import com.automobile.notification.serviceOrder.model.ServiceOrder;

@Service("dealerService")
public class DealerServiceImpl implements DealerService {
	
	@Autowired
	private DealerDAO dealerDAO;
	
	@Autowired
	private VehicleModelDAO vehicleModelDAO;
	
	public void processDealer(ServiceOrder serviceOrder) throws DealerException,VehicleModelException{
		DealerEntity dealerEntity = extractDealerFromServiceOrder(serviceOrder);
		DealerValidator.validateAttributes(dealerEntity);
		VehicleModelValidator.validateAttributes(serviceOrder);
		
		DealerEntity dealer=dealerDAO.getDealerByNameOrId(dealerEntity.getDealerName(),dealerEntity.getDealerId());
		if(dealer==null){
				dealerDAO.create(dealerEntity);
		}
		else{
			if(dealer.getDealerId()==null &&dealerEntity.getDealerId()!=null){
				dealerDAO.updateDealerId(dealerEntity.getDealerId(),dealer.getDealerName());
			}else if(dealer.getDealerName()==null&&dealerEntity.getDealerName()!=null){
				dealerDAO.updateDealerName(dealer.getDealerId(),dealerEntity.getDealerName());
			}
		}
		VehicleModelEntity vehicleModelEntity= extractVehicleModelFromServiceOrder(serviceOrder);
		VehicleModelEntity vehicleModel=vehicleModelDAO.getModelById(vehicleModelEntity.getModelId());
		if(vehicleModel==null){
			vehicleModelEntity.setDealerId(dealerEntity.getDealerId());
			vehicleModelDAO.create(vehicleModelEntity);
		}
	}
	
	public DealerEntity extractDealerFromServiceOrder(ServiceOrder serviceOrder) throws DealerException{
		try{DealerEntity dealerEntity=new DealerEntity();
		dealerEntity.setDealerId(serviceOrder.getDealerId());
		dealerEntity.setDealerName(serviceOrder.getDealerName());
		return dealerEntity;
		}catch(Exception e){
			e.printStackTrace();
			throw new DealerException("Error in extacting Dealer Details from Service Order.");
		}
	}

	public VehicleModelEntity extractVehicleModelFromServiceOrder(ServiceOrder serviceOrder) throws VehicleModelException{
	try{
		VehicleModelEntity vehicleModelEntity =new VehicleModelEntity();
		vehicleModelEntity.setModelId(serviceOrder.getVehicleModelId());
		vehicleModelEntity.setModelName(serviceOrder.getVehicleModelName());
		return vehicleModelEntity; 
	}catch(Exception e){
		e.printStackTrace();
		throw new VehicleModelException("Error in extracting Vehicle Model from Service Order.");
	}
	}
}
