package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Archivo;
import ec.org.pms.models.Estado;
import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.repositories.ArchivoRepository;
import ec.org.pms.repositories.EstadoRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.services.MunicipioService;

@Service("municipioService")
public class MunicipioServiceImpl implements MunicipioService {
	
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private ProvinciaRepository provinciaRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ArchivoRepository archivoRepository;

	@Override
	public List<MunicipiosAddResponse> findMunicipiosAdd() {
		List<Municipio> municipios = new ArrayList<>();
		List<MunicipiosAddResponse> municipiosAdd = new ArrayList<>();
		municipios = municipioRepository.findAllByEstado(901);
		for (Municipio municipio : municipios) {
			Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
			Estado estado = estadoRepository.findByIdAndTipo(municipio.getEstado(), 900);
			MunicipiosAddResponse munAdd = new MunicipiosAddResponse();
			munAdd.setCantonId(municipio.getId());
			munAdd.setCanton(municipio.getCanton());
			munAdd.setProvinciaId(municipio.getProvinciaId());
			munAdd.setProvincia(prov.getProvincia());
			Municipio mun = municipioRepository.findById(municipio.getId()).get();
			if(municipio.getDocumento().length() > 0) {
				munAdd.setArchivo(true);
			}else {
				munAdd.setArchivo(false);
			}
			munAdd.setFecha(municipio.getDia() + "-" + municipio.getMes() + "-" + municipio.getAnio());
			munAdd.setFechaUS(municipio.getMes() + "-" + municipio.getDia() + "-" + municipio.getAnio());
			munAdd.setObservaciones(municipio.getObservaciones());
			munAdd.setEstado(estado.getEstado());
			municipiosAdd.add(munAdd);
		}
		return municipiosAdd;
	}

	@Override
	public List<MunicipiosAddResponse> saveMunicipio(MunicipioAdherirRequest municipio) {
		
			Municipio muni = municipioRepository.findById(municipio.getId()).get();
			muni.setEstado(municipio.getEstado());
			if (municipio.getArchivo().length() > 1){
				muni.setDocumento(municipio.getArchivo());
			}
			muni.setObservaciones(municipio.getObservaciones());
			Calendar cal = Calendar.getInstance();
			cal.setTime(municipio.getFechaAdd());
			muni.setAnio(cal.get(Calendar.YEAR));
			muni.setAnioFin(cal.get(Calendar.YEAR));
			muni.setMes( cal.get(Calendar.MONTH) + 1);
			muni.setMesFin( cal.get(Calendar.MONTH) + 1);
			muni.setDia(cal.get(Calendar.DAY_OF_MONTH));
			municipioRepository.save(muni);
			/*
			Archivo arc = new Archivo();
			arc.setCantonId(muni.getId());
			arc.setArchivo(muni.getDocumento());
			arc.setTipo("pdf");
			arc.setTamanio(2000);
			archivoRepository.save(arc); 
			*/
			
		return findMunicipiosAdd();
	}

}
