package ec.org.pms.services.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Documento;
import ec.org.pms.models.Estado;
import ec.org.pms.models.Indicador;
import ec.org.pms.models.Municipio;
import ec.org.pms.models.Provincia;
import ec.org.pms.models.UserRole;
import ec.org.pms.models.ValorIndicador;
import ec.org.pms.payload.request.MunicipioAdherirRequest;
import ec.org.pms.payload.response.MunicipiosAddResponse;
import ec.org.pms.payload.response.SemaforoMunicipiosResponse;
import ec.org.pms.repositories.DocumentoRepository;
import ec.org.pms.repositories.EstadoRepository;
import ec.org.pms.repositories.IndicadorRepository;
import ec.org.pms.repositories.MunicipioRepository;
import ec.org.pms.repositories.ProvinciaRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.repositories.ValorIndicadorRepository;
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
	private DocumentoRepository docRepository;
	@Autowired
	private IndicadorRepository indicadorRepository;
	@Autowired
	private ValorIndicadorRepository valorIndicadorRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<MunicipiosAddResponse> findMunicipiosAdd() {
		List<Municipio> municipios = new ArrayList<>();
		List<MunicipiosAddResponse> municipiosAdd = new ArrayList<>();
		municipios = municipioRepository.findAllByEstadoAndProvinciaIdLessThan(901, 30);
		for (Municipio municipio : municipios) {
			Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
			Estado estado = estadoRepository.findByIdAndTipo(municipio.getEstado(), 900);
			MunicipiosAddResponse munAdd = new MunicipiosAddResponse();
			munAdd.setCantonId(municipio.getId());
			munAdd.setCanton(municipio.getCanton());
			munAdd.setProvinciaId(municipio.getProvinciaId());
			munAdd.setProvincia(prov.getProvincia());
			// Municipio mun = municipioRepository.findById(municipio.getId()).get();
			if (municipio.getDocumento() != null) {
				munAdd.setArchivo(true);
			} else {
				munAdd.setArchivo(false);
			}
			munAdd.setFecha(municipio.getDia() + "-" + municipio.getMes() + "-" + municipio.getAnio());
			munAdd.setFechaUS(municipio.getMes() + "-" + municipio.getDia() + "-" + municipio.getAnio());
			munAdd.setObservaciones(municipio.getObservaciones());
			munAdd.setEstado(estado.getEstado());
			municipiosAdd.add(munAdd);
		}
		List<SemaforoMunicipiosResponse> semMuniList = new ArrayList<>();
		SemaforoMunicipiosResponse semMuni;
		List<Municipio> municipiosSem = (List<Municipio>) municipioRepository.findAll();
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		int numIndicadores = indicadorRepository.countByIdGreaterThan(0);
		for (Municipio municipio : municipiosSem) {
			int i = 0;
			List<ValorIndicador> valores = valorIndicadorRepository.findByCantonId(municipio.getId());
			for (ValorIndicador valor : valores) {
				for (Indicador indicador : indicadores) {
					if (valor.getEjeId().equals(indicador.getId())) {
						String indic = indicador.getType();
						indic = Normalizer.normalize(indic, Normalizer.Form.NFD);
						indic = indic.replaceAll("[^\\p{ASCII}]", "");
						if (indic.equals("Opciones") || indic.equals("Tipo") || indic.equals("Diferencia de Tasas")
								|| indic.equals("Variacion Anual") || indic.equals("Cantidad")) {
							if (valor.getValor().equals(3.0)) {
								i = i + 1;
							}
						} else {
							if (Double.parseDouble(indicador.getLimite3()) < Double
									.parseDouble(indicador.getLimite4())) {
								if (valor.getValor() > Double.parseDouble(indicador.getLimite3())
										&& valor.getValor() < Double.parseDouble(indicador.getLimite4())) {
									i = i + 1;
								}
							} else {
								if (valor.getValor() < Double.parseDouble(indicador.getLimite3())
										&& valor.getValor() > Double.parseDouble(indicador.getLimite4())) {
									i = i + 1;
								}
							}
						}
					}
				}
			}
			if (i > 0) {
				i = (i * 100) / numIndicadores;
				if (i > 85 && i <= 100) {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("SALUDABLE");
				} else if (i <= 85 && i > 60) {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("GARANTE");
				} else if (i <= 60 && i > 35) {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("PROMOTOR");
				} else {
					semMuni = new SemaforoMunicipiosResponse();
					semMuni.setCantonId(municipio.getId());
					semMuni.setSemaforo("NOCERTIFICA");
				}
			} else {
				semMuni = new SemaforoMunicipiosResponse();
				semMuni.setCantonId(municipio.getId());
				semMuni.setSemaforo("NOCERTIFICA");
			}
			semMuniList.add(semMuni);
		}
		for (SemaforoMunicipiosResponse semMunis : semMuniList) {
			for (MunicipiosAddResponse munAdd : municipiosAdd) {
				if (semMunis.getCantonId().equals(munAdd.getCantonId()))
					munAdd.setSemaforo(semMunis.getSemaforo());
			}
		}
		return municipiosAdd;
	}

	@Override
	public List<MunicipiosAddResponse> saveMunicipio(MunicipioAdherirRequest municipio) {

		Municipio muni = municipioRepository.findById(municipio.getId()).get();
		muni.setEstado(municipio.getEstado());
		muni.setEstadoCtf(200); // estado ingresando valores de indicadores
		if (municipio.getArchivo().length() > 1) {
			Documento doc = new Documento();
			doc = docRepository.findFirstByOrderByIdDesc();
			doc.setId(doc.getId() + 1);
			doc.setTypeId(1);
			doc.setDocumento(municipio.getArchivo());
			doc = docRepository.save(doc);
			muni.setDocumento(doc.getId());
		}
		muni.setObservaciones(municipio.getObservaciones());
		Calendar cal = Calendar.getInstance();
		cal.setTime(municipio.getFechaAdd());
		muni.setAnio(cal.get(Calendar.YEAR));
		muni.setAnioFin(cal.get(Calendar.YEAR));
		muni.setMes(cal.get(Calendar.MONTH) + 1);
		muni.setMesFin(cal.get(Calendar.MONTH) + 1);
		muni.setDia(cal.get(Calendar.DAY_OF_MONTH));
		municipioRepository.save(muni);
		/*
		 * Archivo arc = new Archivo(); arc.setCantonId(muni.getId());
		 * arc.setArchivo(muni.getDocumento()); arc.setTipo("pdf");
		 * arc.setTamanio(2000); archivoRepository.save(arc);
		 */

		return findMunicipiosAdd();
	}

	@Override
	public List<MunicipiosAddResponse> findMunicipiosEva() {
		List<Municipio> municipios = new ArrayList<>();
		List<MunicipiosAddResponse> municipiosAdd = new ArrayList<>();
		municipios = municipioRepository.findAllByEstadoCtf(201);
		for (Municipio municipio : municipios) {
			Provincia prov = provinciaRepository.findByCodigoAndBandera(municipio.getProvinciaId(), 100);
			Estado estado = estadoRepository.findByIdAndTipo(municipio.getEstado(), 900);
			MunicipiosAddResponse munAdd = new MunicipiosAddResponse();
			munAdd.setCantonId(municipio.getId());
			munAdd.setCanton(municipio.getCanton());
			munAdd.setProvinciaId(municipio.getProvinciaId());
			munAdd.setProvincia(prov.getProvincia());
			// Municipio mun = municipioRepository.findById(municipio.getId()).get();
			if (municipio.getDocumento() != null) {
				munAdd.setArchivo(true);
			} else {
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
	public List<MunicipiosAddResponse> saveMunicipioLink(MunicipioAdherirRequest municipio) {
		UserRole user = userRoleRepository.findById(municipio.getId()).get();
		Integer cantonId = user.getEntId();
		Municipio mun = municipioRepository.findById(cantonId).get();
		Documento doc = new Documento();
		Documento docAux = new Documento();
		if (mun.getDrive_id() != null) {
			doc = docRepository.findById(mun.getDrive_id()).get();
			doc.setDocumento(municipio.getArchivo());
			doc = docRepository.save(doc);
		} else {
			doc = docRepository.findFirstByOrderByIdDesc();
			docAux.setId(doc.getId() + 1);
			docAux.setTypeId(3);
			docAux.setDocumento(municipio.getArchivo());
			docAux = docRepository.save(docAux);
			mun.setDrive_id(docAux.getId());
			municipioRepository.save(mun);
		}
		return null;
	}

	@Override
	public List<MunicipiosAddResponse> saveMunicipioRequest(MunicipioAdherirRequest municipio) {
		UserRole user = userRoleRepository.findById(municipio.getId()).get();
		Integer cantonId = user.getEntId();
		Municipio mun = municipioRepository.findById(cantonId).get();
		Documento doc = new Documento();
		Documento docAux = new Documento();
		if (mun.getEvaluationId() != null) {
			doc = docRepository.findById(mun.getEvaluationId()).get();
			doc.setDocumento(municipio.getArchivo());
			doc = docRepository.save(doc);
		} else {
			doc = docRepository.findFirstByOrderByIdDesc();
			docAux.setId(doc.getId() + 1);
			docAux.setTypeId(4);
			docAux.setDocumento(municipio.getArchivo());
			docAux = docRepository.save(docAux);
			mun.setEvaluationId(docAux.getId());
			municipioRepository.save(mun);
		}
		return null;
	}

	@Override
	public MunicipioAdherirRequest findMunicipio(Integer id) {
		UserRole user = userRoleRepository.findById(id).get();
		Integer cantonId = user.getEntId();
		Municipio muni = municipioRepository.findById(cantonId).get();
		MunicipioAdherirRequest munSend = new MunicipioAdherirRequest();
		munSend.setId(muni.getId());
		Documento doc = new Documento();
		if (muni.getEvaluationId() != null)
			munSend.setSolicitud(true);
		else
			munSend.setSolicitud(false);
		if (muni.getDrive_id() != null) {
			doc = docRepository.findById(muni.getDrive_id()).get();
			munSend.setDriveLink(doc.getDocumento());
		} else {
			munSend.setDriveLink("");
		}
		return munSend;
	}

	@Override
	public MunicipioAdherirRequest findLink(Integer id) {
		Municipio muni = municipioRepository.findById(id).get();
		MunicipioAdherirRequest munSend = new MunicipioAdherirRequest();
		munSend.setId(muni.getId());
		Documento doc = new Documento();
		if (muni.getEvaluationId() != null)
			munSend.setSolicitud(true);
		else
			munSend.setSolicitud(false);
		if (muni.getDrive_id() != null) {
			doc = docRepository.findById(muni.getDrive_id()).get();
			munSend.setDriveLink(doc.getDocumento());
		} else {
			munSend.setDriveLink("");
		}
		return munSend;
	}

}
