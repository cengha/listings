package com.heycar.listings;

import com.heycar.listings.infrastructure.persistence.jooq.generated.listings.Tables;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableRecordImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DbCleaner {

  @Autowired
  private DSLContext dslContext;

  final List<TableImpl<? extends TableRecordImpl<? extends TableRecordImpl<?>>>> dbTables = List.of(
    Tables.LISTINGS
  );

  void execute() {
    dbTables.forEach(table -> dslContext.deleteFrom(table).execute());
  }

}
