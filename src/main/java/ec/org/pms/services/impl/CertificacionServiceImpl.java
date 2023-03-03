package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import ec.org.pms.models.Documento;
import ec.org.pms.models.Eje;
import ec.org.pms.models.Indicador;
import ec.org.pms.models.UserRole;
import ec.org.pms.models.ValorIndicador;
import ec.org.pms.payload.request.IndicadorRequest;
import ec.org.pms.payload.response.SemaforizacionResponse;
import ec.org.pms.payload.response.ejeResponse.DatosBarra;
import ec.org.pms.payload.response.ejeResponse.DatosEje;
import ec.org.pms.payload.response.indicadorResponse.Datos;
import ec.org.pms.payload.response.indicadorResponse.Hijo;
import ec.org.pms.payload.response.indicadorResponse.Padre;
import ec.org.pms.payload.response.indicadorResponse.Root;
import ec.org.pms.payload.response.componenteResponse.Componente;
import ec.org.pms.payload.response.componenteResponse.HijoComponente;
import ec.org.pms.repositories.DocumentoRepository;
import ec.org.pms.repositories.EjeRepository;
import ec.org.pms.repositories.IndicadorRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.repositories.ValorIndicadorRepository;
import ec.org.pms.services.CertificacionService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;;

@Service("certificacionService")
public class CertificacionServiceImpl implements CertificacionService {

	@Autowired
	private EjeRepository ejeRepository;
	@Autowired
	private IndicadorRepository indicadorRepository;
	@Autowired
	private ValorIndicadorRepository valorIndicadorRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private DocumentoRepository docRepository;
	@Autowired
	private DataSource dataSource;

	@Override
	public List<Root> findIndicadores(Integer personaId) {
		List<Eje> ejes = ejeRepository.findByIdLessThanOrderByIdAsc(10);
		List<Eje> determinantes = ejeRepository.findByIdGreaterThan(10);
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		UserRole user = userRoleRepository.findById(personaId).get();
		Integer cantonId = user.getEntId();
		List<ValorIndicador> resultados = valorIndicadorRepository.findByCantonId(cantonId);
		Datos data;
		Hijo childre;
		List<Hijo> childres;
		List<Hijo> childress;
		Padre children;
		List<Padre> childrens;
		List<Padre> childrenss;
		Root root;
		List<Root> roots;

		childres = new ArrayList<>();
		for (Indicador indicador : indicadores) {
			data = new Datos();
			data.setIndicadorId(indicador.getId());
			data.setEjeId(indicador.getEjeId());
			data.setCode(indicador.getCode());
			data.setName(indicador.getName());
			data.setDescription(indicador.getDescription());
			data.setType(indicador.getType());
			data.setCuantitativo(indicador.isQuantitative());
			for (ValorIndicador valorIndicador : resultados) {
				if (indicador.getId().equals(valorIndicador.getEjeId())
						&& valorIndicador.getCantonId().equals(cantonId)) {
					data.setValueIndicadorId(valorIndicador.getId());
					if (valorIndicador != null && valorIndicador.getEjeId() > 0) {
						data.setValue(String.valueOf(valorIndicador.getValor()));
					} else {
						data.setValue(null);
					}
					if (valorIndicador.getAnio() != null && valorIndicador.getMes() != null
							&& valorIndicador.getDia() != null) {
						data.setDate(
								String.valueOf(valorIndicador.getMes()) + "-" + String.valueOf(valorIndicador.getDia())
										+ "-" + String.valueOf(valorIndicador.getAnio()));
					}
					if (valorIndicador.getFecha() != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(valorIndicador.getFecha());
						data.setDateRegister(String.valueOf(cal.get(Calendar.MONTH) + 1) + "-"
								+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "-"
								+ String.valueOf(cal.get(Calendar.YEAR)));
					}
					if (valorIndicador.getObservacion() != null) {
						data.setObs(valorIndicador.getObservacion());
					} else {
						data.setObs("");
					}
					if (valorIndicador.getFuente() != null) {
						data.setFont(valorIndicador.getFuente());
					}
					/*if (valorIndicador.getArchivo() != null) {
						data.setArchivo(true);
					} else {
						data.setArchivo(false);
					}*/
				}
			}
			data.setOption1(indicador.getInicial());
			data.setOption2(indicador.getSatisfactorio());
			data.setOption3(indicador.getOptimo());
			if (indicador.isQuantitative()) {
				data.setLimite1(Integer.valueOf(indicador.getLimite1()));
				data.setLimite2(Integer.valueOf(indicador.getLimite2()));
				data.setLimite3(Double.valueOf(indicador.getLimite3()));
				data.setLimite4(Integer.valueOf(indicador.getLimite4()));
			}
			data.setObligatory(indicador.isObliged());
			data.setDesnutrition(indicador.isDesnutrition());
			data.setMaternity(indicador.isMaternity());
			data.setViolence(indicador.isViolence());
			childre = new Hijo();
			childre.setKey(String.valueOf(indicador.getId()));
			childre.setData(data);
			childres.add(childre);
		}

		childrens = new ArrayList<>();
		for (Eje determinante : determinantes) {
			children = new Padre();
			children.setKey(String.valueOf(determinante.getId()));
			data = new Datos();
			data.setName(determinante.getName());
			data.setEjeId(determinante.getParent());
			children.setData(data);
			childress = new ArrayList<>();
			for (Hijo hijo : childres) {
				if (determinante.getId().equals(hijo.getData().getEjeId())) {
					childress.add(hijo);
				}
			}
			children.setChildren(childress);
			childrens.add(children);
		}

		roots = new ArrayList<>();
		for (Eje eje : ejes) {
			root = new Root();
			root.setKey(String.valueOf(eje.getId() - 1));
			data = new Datos();
			data.setName(eje.getName());
			data.setEjeId(eje.getId());
			root.setData(data);
			childrenss = new ArrayList<>();
			for (Padre childen : childrens) {
				if (eje.getId().equals(childen.getData().getEjeId())) {
					childrenss.add(childen);
				}
			}
			root.setChildren(childrenss);
			roots.add(root);
		}
		int i = 0;
		for (Root roo : roots) {
			List<Padre> padres = roo.getChildren();
			int j = 0;
			for (Padre padre : padres) {
				padre.setKey(roo.getKey() + "-" + String.valueOf(j));
				List<Hijo> hijos = padre.getChildren();
				int k = 0;
				for (Hijo hijo : hijos) {
					hijo.setKey(roo.getKey() + "-" + String.valueOf(j) + "-" + String.valueOf(k));
					k = k + 1;
				}
				j = j + 1;
			}
			i = i + 1;
		}
		return roots;
	}

	@Override
	public List<Root> saveIndicador(IndicadorRequest indicadorSave) {
		ValorIndicador indicador = new ValorIndicador();
		int level = indicadorRepository.findBycode(Integer.valueOf(indicadorSave.getCode())).getId();
		UserRole userRol = userRoleRepository.findFirstByPersonId(indicadorSave.getUserId());
		if (indicadorSave.getArchivo().length() > 1) {
			Documento doc = docRepository.findFirstByOrderByIdDesc();
			Documento docAux = new Documento();
			docAux.setId(doc.getId() + 1);
			docAux.setTypeId(2);
			docAux.setDocumento(indicadorSave.getArchivo());
			docRepository.save(docAux);
			indicador.setArchivo(docAux.getId());
		} else {
			indicador = valorIndicadorRepository.findById(indicadorSave.getValueIndicadorId()).get();
		}
		if (indicadorSave.getValueIndicadorId() != null) {
			indicador.setId(indicadorSave.getValueIndicadorId());
		} else {
			ValorIndicador indicadorAux = valorIndicadorRepository.findFirstByOrderByIdDesc();
			indicador.setId(indicadorAux.getId() + 1);
		}
		indicador.setCantonId(userRol.getEntId());
		indicador.setEjeId(level);
		indicador.setValor(Double.valueOf(indicadorSave.getValue()));
		if (indicadorSave.getDate() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(indicadorSave.getDate());
			indicador.setAnio(cal.get(Calendar.YEAR));
			indicador.setMes((cal.get(Calendar.MONTH) + 1));
			indicador.setDia(cal.get(Calendar.DAY_OF_MONTH));
		}
		if (indicadorSave.getDateRegister() != null) {
			indicador.setFecha(indicadorSave.getDateRegister());
		}
		indicador.setFuente(indicadorSave.getFont());
		indicador.setObservacion(indicadorSave.getObs());
		indicador.setPersonaId(userRol.getId());
		indicador.setEstado(91);

		indicador = valorIndicadorRepository.save(indicador);

		return findIndicadores(indicadorSave.getUserId());
	}

	@Override
	public List<SemaforizacionResponse> semaforizacion(Integer cantonId) {
		List<Eje> ejes = ejeRepository.findByIdLessThanOrderByIdAsc(10);
		List<Eje> determinantes = ejeRepository.findByIdGreaterThan(10);
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		SemaforizacionResponse semaforo;
		List<SemaforizacionResponse> semaforos = new ArrayList<>();

		for (Eje eje : ejes) {
			semaforo = new SemaforizacionResponse();
			semaforo.setEje(eje.getName());
			semaforos.add(semaforo);
			for (Eje determinante : determinantes) {
				if (determinante.getParent().equals(eje.getId())) {
					semaforo = new SemaforizacionResponse();
					semaforo.setDeterminante(determinante.getName());
					semaforos.add(semaforo);
					for (Indicador indicador : indicadores) {
						if (indicador.getEjeId().equals(determinante.getId())) {
							semaforo = new SemaforizacionResponse();
							semaforo.setCodigo(String.valueOf(indicador.getCode()));
							semaforo.setIndicador(indicador.getName());
							//UserRole user = userRoleRepository.findById(personaId).get();
							//Integer cantonId = user.getEntId();
							//System.out.println(indicador.getId());
							ValorIndicador resultado = valorIndicadorRepository.findByCantonIdAndEjeId(cantonId, indicador.getId());
							if (resultado != null) {
								double DoubleValue = resultado.getValor();
								semaforo.setValor(resultado.getValor());
								semaforo.setTipo(indicador.getType());
								semaforo.setValorIndicadorId(resultado.getId());
								semaforo.setVoucher(resultado.getArchivo() != null ? true : false);
								int IntValue = (int) DoubleValue;

								if (!indicador.isQuantitative()) {
									switch (IntValue) {
									case 1:
										semaforo.setSemaforizacion("red");
										break;
									case 2:
										semaforo.setSemaforizacion("yellow");
										break;
									case 3:
										semaforo.setSemaforizacion("green");
										break;
									default:
										break;
									}
								} else if (Integer.parseInt(indicador.getLimite1()) < Integer
										.parseInt(indicador.getLimite4())) {
									if (Integer.parseInt(indicador.getLimite1()) <= IntValue
											&& IntValue <= Integer.parseInt(indicador.getLimite2())) {
										semaforo.setSemaforizacion("red");
									} else if (Integer.parseInt(indicador.getLimite2()) < IntValue
											&& IntValue <= Integer.parseInt(indicador.getLimite3())) {
										semaforo.setSemaforizacion("yellow");
									} else if (Integer.parseInt(indicador.getLimite3()) < IntValue
											&& IntValue <= Integer.parseInt(indicador.getLimite4())) {
										semaforo.setSemaforizacion("green");
									}
								} else {
									if (Integer.parseInt(indicador.getLimite1()) >= IntValue
											&& IntValue >= Integer.parseInt(indicador.getLimite2())) {
										semaforo.setSemaforizacion("red");
									} else if (Integer.parseInt(indicador.getLimite2()) > IntValue
											&& IntValue >= Double.valueOf(indicador.getLimite3())) {
										semaforo.setSemaforizacion("yellow");
									} else if (Double.valueOf(indicador.getLimite3()) > IntValue
											&& IntValue >= Integer.parseInt(indicador.getLimite4())) {
										semaforo.setSemaforizacion("green");
									}
								}
							}
							semaforos.add(semaforo);
						}
					}
				}
			}
		}
		return semaforos;
	}

	@Override
	public DatosBarra ejes(Integer personaId) {
		List<Eje> ejes = ejeRepository.findByIdLessThanOrderByIdAsc(10);
		List<Eje> determinantes = ejeRepository.findByIdGreaterThan(10);
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		DatosBarra barData = new DatosBarra();
		DatosEje datosEje;
		List<DatosEje> datosEjes = new ArrayList<>();
		String[] ejesArreglo = new String[7];
		Integer[] verdesArreglo = new Integer[7];
		Integer[] amarrillosArreglo = new Integer[7];
		Integer[] rojosArreglo = new Integer[7];
		int verdes = 0;
		int amarillos = 0;
		int rojos = 0;

		UserRole user = userRoleRepository.findById(personaId).get();
		Integer cantonId = user.getEntId();

		int i = 0;
		for (Eje eje : ejes) {
			ejesArreglo[i] = eje.getName();
			verdes = 0;
			amarillos = 0;
			rojos = 0;
			for (Eje determinante : determinantes) {
				if (determinante.getParent().equals(eje.getId())) {

					for (Indicador indicador : indicadores) {
						if (indicador.getEjeId().equals(determinante.getId())) {
							ValorIndicador resultado = valorIndicadorRepository.findByCantonIdAndEjeId(cantonId,
									indicador.getId());
							if (resultado != null) {
								double DoubleValue = resultado.getValor();
								int IntValue = (int) DoubleValue;

								if (!indicador.isQuantitative()) {
									switch (IntValue) {
									case 1:
										rojos = rojos + 1;
										break;
									case 2:
										amarillos = amarillos + 1;
										break;
									case 3:
										verdes = verdes + 1;
										break;
									default:
										break;
									}
								} else if (Integer.parseInt(indicador.getLimite1()) < Integer
										.parseInt(indicador.getLimite4())) {
									if (Integer.parseInt(indicador.getLimite1()) <= IntValue
											&& IntValue <= Integer.parseInt(indicador.getLimite2())) {
										rojos = rojos + 1;
									} else if (Integer.parseInt(indicador.getLimite2()) < IntValue
											&& IntValue <= Integer.parseInt(indicador.getLimite3())) {
										amarillos = amarillos + 1;
									} else if (Integer.parseInt(indicador.getLimite3()) < IntValue
											&& IntValue <= Integer.parseInt(indicador.getLimite4())) {
										verdes = verdes + 1;
									}
								} else {
									if (Integer.parseInt(indicador.getLimite1()) >= IntValue
											&& IntValue >= Integer.parseInt(indicador.getLimite2())) {
										rojos = rojos + 1;
									} else if (Integer.parseInt(indicador.getLimite2()) > IntValue
											&& IntValue >= Double.valueOf(indicador.getLimite3())) {
										amarillos = amarillos + 1;
									} else if (Double.valueOf(indicador.getLimite3()) > IntValue
											&& IntValue >= Integer.parseInt(indicador.getLimite4())) {
										verdes = verdes + 1;
									}
								}
							}
						}
					}
				}
			}
			verdesArreglo[i] = verdes;
			amarrillosArreglo[i] = amarillos;
			rojosArreglo[i] = rojos;
			i = i + 1;
		}
		datosEje = new DatosEje();
		datosEje.setLabel("Optimos");
		datosEje.setBackgroundColor("green");
		datosEje.setData(verdesArreglo);

		datosEjes.add(datosEje);

		datosEje = new DatosEje();
		datosEje.setLabel("Satisfactorios");
		datosEje.setBackgroundColor("yellow");
		datosEje.setData(amarrillosArreglo);

		datosEjes.add(datosEje);

		datosEje = new DatosEje();
		datosEje.setLabel("Iniciales");
		datosEje.setBackgroundColor("red");
		datosEje.setData(rojosArreglo);

		datosEjes.add(datosEje);

		barData.setDatasets(datosEjes);
		barData.setLabels(ejesArreglo);

		return barData;
	}

	@Override
	public List<Componente> findComponentes() {
		List<Eje> ejes = ejeRepository.findByIdLessThanOrderByIdAsc(10);
		List<Componente> comps = new ArrayList<>();
		for (Eje eje : ejes) {
			Componente comp = new Componente();
			comp.setId(String.valueOf(eje.getId()));
			comp.setName(eje.getName());
			comp.setImage(eje.getPath());
			comps.add(comp);
		}
		return comps;
	}

	@Override
	public List<HijoComponente> findComponenteDetalle(Integer componenteId) {
		Eje eje = ejeRepository.findById(componenteId).get();
		List<Eje> determinantes = ejeRepository.findByParent(componenteId);
		List<HijoComponente> ejes = new ArrayList<>();
		List<HijoComponente> data = new ArrayList<>();

		HijoComponente hEje;
		List<HijoComponente> hIndi;

		HijoComponente hComp = new HijoComponente();
		hComp.setLabel("Componente");
		hComp.setType("componente");
		hComp.setClassName("p-person");
		hComp.setExpanded(true);
		Componente det = new Componente();
		det.setName(eje.getName());
		det.setImage(eje.getPath());
		hComp.setData(det);

		for (Eje determinante : determinantes) {
			hEje = new HijoComponente();
			hEje.setLabel(determinante.getName());
			hEje.setType("eje");
			hEje.setClassName("eje");
			hEje.setExpanded(true);
			List<Indicador> indicadoresAux = (List<Indicador>) indicadorRepository.findByEjeId(determinante.getId());
			hIndi = new ArrayList<>();
			for (Indicador indi : indicadoresAux) {
				HijoComponente hIndic = new HijoComponente();
				hIndic.setLabel(indi.getName());
				hIndic.setType("indicador");
				hIndic.setClassName("p-person");
				hIndic.setExpanded(true);
				Componente detalle = new Componente();
				detalle.setName(indi.getDescription());
				hIndic.setData(detalle);
				hIndi.add(hIndic);
			}
			hEje.setChildren(hIndi);
			ejes.add(hEje);
		}
		hComp.setChildren(ejes);
		data.add(hComp);
		return data;
	}

	@Override
	public String comprobanteIndicador(Integer valorIndicadorId) {
		ValorIndicador indicadorData = valorIndicadorRepository.findById(valorIndicadorId).get();
		Documento doc = docRepository.findById(indicadorData.getArchivo()).get();
		return doc.getDocumento();
	}
	
	@Override
	public String createCertificado(Integer cantonId) {
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("municipioId", cantonId);

			JasperPrint empReport = JasperFillManager.fillReport(
					JasperCompileManager.compileReport(
							ResourceUtils.getFile("classpath:reports/certificate.jrxml").getAbsolutePath()),
					parameters // dynamic parameters
					, dataSource.getConnection());

			byte[] pdf = JasperExportManager.exportReportToPdf(empReport);
			String pdfBas64 = Base64.getEncoder().encodeToString(pdf);
			return "data:application/pdf;base64," + pdfBas64;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	@Override
	public String createCertificadoPerson(Integer personaId) {
		try {
			Map<String, Object> parameters = new HashMap<>();
			UserRole user = userRoleRepository.findById(personaId).get();
			parameters.put("municipioId", user.getEntId());

			JasperPrint empReport = JasperFillManager.fillReport(
					JasperCompileManager.compileReport(
							ResourceUtils.getFile("classpath:reports/certificate.jrxml").getAbsolutePath()),
					parameters // dynamic parameters
					, dataSource.getConnection());

			byte[] pdf = JasperExportManager.exportReportToPdf(empReport);
			String pdfBas64 = Base64.getEncoder().encodeToString(pdf);
			return "data:application/pdf;base64," + pdfBas64;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

}
