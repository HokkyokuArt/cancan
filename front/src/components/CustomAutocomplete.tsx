import { Autocomplete, TextField } from '@mui/material';
import { useEffect, useState } from 'react';
import useSuperRequests from '../common/services/useSuperRequests';
import type { AbstractEntityDTO } from '../common/types/abstractEntity';
import { Pageable, type Filter } from '../common/types/pageable';
import type { UUID } from '../common/types/uuid';

type Props<F extends Filter> = {
    id: string;
    label: string;
    disabled?: boolean;
    extraCriterias?: Omit<F, 'busca' | 'buscaSimples'>;
} & (
        {
            isAsync: true;
            path: string;

        } |
        {
            isAsync: false;
            options: AbstractEntityDTO[];
        }
    )
    & (
        {
            multiple: true;
            onSelect: (value: UUID[]) => void;
            value?: UUID[];
        } |
        {
            multiple?: false;
            onSelect: (value: UUID | null) => void;
            value?: UUID;
        }
    );

//  implementar versão sincrona
const CustomAutocomplete = <F extends Filter = Filter>(props: Props<F>) => {
    const [value, setValue] = useState<AbstractEntityDTO[]>([]);
    const [options, setOptions] = useState<AbstractEntityDTO[]>(!props.isAsync ? props.options : []);

    const {
        findAbstractEntity,
        autocomplete,
    } = useSuperRequests({ url: props.isAsync ? props.path : '' });

    useEffect(() => {
        if (props.isAsync) {
            if (!!props.value) {
                findAbstractEntity(props.multiple ? props.value : [props.value],
                    res => setValue(res));
            } else {
                setTimeout(() => {
                    setValue([]);
                },);
            }
            handleInputChange('');
        }
    }, [props.value]);

    const handleInputChange = (busca: string) => {
        const pageable = Pageable.of({
            page: 0,
            size: 25,
            sort: []
        });
        autocomplete(
            { ...props.extraCriterias, buscaSimples: busca },
            pageable,
            res => {
                setOptions(res.content);
            });
    };

    const handleSelect = (value: AbstractEntityDTO | AbstractEntityDTO[] | null) => {
        const v = Array.isArray(value) ? value : (!value ? [] : [value]);
        setValue(v);
        const ids = v.map(s => s.id);
        if (props.multiple) {
            props.onSelect(ids);
        } else {
            props.onSelect(ids[0] ?? null);
        }
    };

    return (
        <Autocomplete
            id={props.id}
            multiple={props.multiple}
            disablePortal
            disabled={props.disabled}
            options={options}
            getOptionLabel={s => s.descritivo}
            onInputChange={(_, value) => handleInputChange(value)}
            onChange={(_, value) => handleSelect(value)}
            value={props.multiple ? value : value[0] ?? null}
            blurOnSelect={true}
            filterOptions={(x) => x}
            noOptionsText={'Nenhum resultado encontrado'}
            isOptionEqualToValue={(option, value) => option.id === value.id}
            renderInput={
                (params) => <TextField {...params}
                    label={props.label}
                    variant="standard"
                />}
        />
    );
};

export default CustomAutocomplete;
