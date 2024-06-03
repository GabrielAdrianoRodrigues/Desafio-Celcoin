package br.com.gabriel.desafio_celcoin.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Tuple;

public abstract class QueryUtils {
    
    public static <T> T fromTuple(Class<T> entity, Tuple tuple) throws Exception {
        T obj = entity.getConstructor().newInstance();
        for(Field x : entity.getDeclaredFields()) {
            entity.getMethod("set"+StringUtils.capitalize(x.getName()), x.getType()).invoke(obj, x.getType().cast(tuple.get(x.getName())));
        }
        return obj;
    }
    
    public static <T> List<T> fromTuples(Class<T> entity, List<Tuple> tuples) throws Exception {
        Field[] fields = entity.getDeclaredFields();
        List<T> entities = new ArrayList<T>();
        T obj;
        for(Tuple tuple : tuples) {
            obj = entity.getConstructor().newInstance();
            for (Field x : fields) {
                entity.getMethod("set"+StringUtils.capitalize(x.getName()), x.getType()).invoke(obj, x.getType().cast(tuple.get(x.getName())));
            }
            entities.add(obj);
        }
        return entities;
    }
}
