export class Pageable {
  page: number;
  size: number;
  sort: Sort | Sort[];

  private constructor(paramns: Pick<Pageable, "page" | "size" | "sort">) {
    this.page = paramns.page;
    this.size = paramns.size;
    this.sort = paramns.sort;
  }

  public static of(params: Pick<Pageable, "page" | "size" | "sort">) {
    return new Pageable(params);
  }

  public static ofPageSize(params: Pick<Pageable, "page" | "size">) {
    return new Pageable({ ...params, sort: [] });
  }

  public build() {
    console.log(
      Array.isArray(this.sort)
        ? this.sort.map((s) => s.build()).join("&")
        : this.sort.build(),
    );
    return {
      page: this.page,
      size: this.size,
      sort: Array.isArray(this.sort)
        ? this.sort.map((s) => s.build()).join("&")
        : this.sort.build(),
    };
  }
}

export type Page<T> = {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  size: number;
  totalElements: number;
  totalPages: number;
};

export type Filter = Record<string, unknown>;

export enum OrderDirection {
  ASC = "asc",
  DESC = "desc",
}

export class Sort {
  field: string;
  order: OrderDirection;

  private constructor(paramns: Pick<Sort, "field" | "order">) {
    this.field = paramns.field;
    this.order = paramns.order;
  }

  public static ofField(field: string) {
    return new Sort({ field, order: OrderDirection.ASC });
  }

  public build() {
    return `${this.field},${this.order}`;
  }
}
