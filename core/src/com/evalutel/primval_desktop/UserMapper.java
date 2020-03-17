package com.evalutel.primval_desktop;

import javax.xml.datatype.Duration;

import de.bitbrain.jpersis.annotations.Count;
import de.bitbrain.jpersis.annotations.Delete;
import de.bitbrain.jpersis.annotations.Ignored;
import de.bitbrain.jpersis.annotations.Insert;
import de.bitbrain.jpersis.annotations.Mapper;
import de.bitbrain.jpersis.annotations.Select;

@Mapper("com.evalutel.primval_desktop.User")
public interface UserMapper
{
    @Insert
    boolean insert(User user);


    @Insert
    boolean insertTemps(Duration duration);

    @Delete
    boolean delete(User user);

    @Count
    int count();

    @Select(condition = "name = $1")
    User findByUserName(String userName);

    @Insert
    void insertTemps(float duration);
}
