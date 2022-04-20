package com.kamaz.services;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.kamaz.exceptions.ServicesException;
import com.kamaz.models.Departament;
import com.kamaz.repository.DepartamentRepository;

@Service
@Transactional
public class DepartamentService {

	LocalDateTime now = LocalDateTime.now();
	Timestamp timestamp = Timestamp.valueOf(now);

	@Autowired
	DepartamentRepository departamentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public static String formatDate(Date date, String format) throws ParseException {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String s = dateFormat.format(date.getTime());
		Locale russian = new Locale("ru");
		String[] newMonths = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября",
				"октября", "ноября", "декабря" };
		DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
		dfs.setMonths(newMonths);
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);
		SimpleDateFormat sdf = (SimpleDateFormat) df;
		sdf.setDateFormatSymbols(dfs);

		Date jud = new SimpleDateFormat(format).parse(s);
		String month = sdf.format(jud);
		return month;
	}

	public static String ConvertJsonDate(String jsondate) {
		jsondate = jsondate.replace("/Date(", "").replace(")/", "");
		long time = Long.parseLong(jsondate);
		Date d = new Date(time);
		return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(d).toString();
	}

	public List<?> newDepartament(Departament positionData) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> hashMap = new HashMap<String, String>();

		ArrayList<String> list = new ArrayList<String>();
		Gson gson = new Gson();
		try {
			Departament newcard = new Departament();
			newcard.setName(positionData.getName());
			newcard.setDateOf(positionData.getDateOf());
			newcard.setDescription(positionData.getDescription());
			Long cardId = departamentRepository.save(newcard).getId();

			departamentRepository.findById(cardId).stream().forEach(e -> {
				try {
					hashMap.put("id", e.getId().toString());
					hashMap.put("name", e.getName());
					hashMap.put("dateOf", formatDate(e.getDateOf(), "dd-MM-yyyy"));
					hashMap.put("description", e.getDescription());
					// Creating ArrayList from Entry set
					ArrayList<Entry<String, String>> entryArrayList = new ArrayList<Entry<String, String>>(
							hashMap.entrySet());
					for (Entry<String, String> entry : entryArrayList) {
						map.put(entry.getKey(), entry.getValue());
					}
					list.add(gson.toJson(map));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			return list;
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}

	}

	public List<Departament> getDepartaments() throws JsonProcessingException {
		List<Departament> data = null;
		try {
			data = departamentRepository.findAll(Sort.by(Direction.ASC, "dateOf"));
			if (!data.isEmpty()) {
				return data;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.getCause().getCause().getLocalizedMessage();
		}
		return data;
	}

	public Departament updateDepartament(Departament departament) {
		try {
			return departamentRepository.findById(departament.getId()).map(pos -> {
				pos.setName(departament.getName());
				pos.setDateOf(departament.getDateOf());
				pos.setDescription(departament.getDescription());
				return departamentRepository.save(pos);
			}).orElseGet(() -> {
				departament.setId(departament.getId());
				return departamentRepository.save(departament);
			});
		} catch (Exception ex) {
			throw new ServicesException(ex.getCause().getCause().getLocalizedMessage());
		}
	}

	public Departament deleteDepartament(long id) {
		Optional<Departament> pos = departamentRepository.findById(id);
		List<Departament> position = pos.stream().map(role -> role).collect(Collectors.toList());
		if (!pos.isEmpty()) {
			Iterator<Departament> iterator = position.iterator();
			while (iterator.hasNext()) {
				Departament ipos = iterator.next();
				if (ipos.getId() == id) {
					iterator.remove();
					departamentRepository.deleteById(ipos.getId());
					return ipos; // returns the deleted resource back
				}
			}
		}
		return null;
	}

}
