import { Autocomplete, TextField } from '@mui/material';
import { useEffect, useState } from 'react';
import useRequestService from '../common/services/useRequestService';
import type { AbstractEnumDTO } from '../common/types/abstractEntity';

type Props = {
    id: string;
    label: string;
    disabled?: boolean;
    filterOptions?: (options: AbstractEnumDTO[]) => AbstractEnumDTO[];
    enumName: string;
    optionLabel?: string;
} & (
        {
            multiple: true;
            onSelect: (value: string[]) => void;
            value?: string[];
        } |
        {
            multiple?: false;
            onSelect: (value: string | null) => void;
            value?: string;
        }
    );


const EnumAutocomplete = (props: Props) => {
    const [value, setValue] = useState<AbstractEnumDTO[]>([]);
    const [options, setOptions] = useState<AbstractEnumDTO[]>([]);
    const { getRequest } = useRequestService();

    useEffect(() => {
        getRequest<AbstractEnumDTO[]>({
            url: `/utils/enum/${props.enumName}`,
            then: res => {
                setOptions(props.filterOptions?.(res) ?? res);
                if (!!props.value) {
                    const values = Array.isArray(props.value) ? props.value : [props.value];
                    setValue(values.map(v => {
                        const found = res.find(r => r.name === v);
                        if (!found) throw new Error('Enum não existe: ' + v);
                        return found;
                    }));
                }
            }
        });
    }, []);

    const handleSelect = (value: AbstractEnumDTO | AbstractEnumDTO[] | null) => {
        const v = Array.isArray(value) ? value : (!value ? [] : [value]);
        setValue(v);
        const names = v.map(s => s.name);
        if (props.multiple) {
            props.onSelect(names);
        } else {
            props.onSelect(names[0] ?? null);
        }
    };

    return (
        <Autocomplete
            id={props.id}
            multiple={props.multiple}
            disablePortal
            disabled={props.disabled}
            options={options}
            getOptionLabel={s => s[props.optionLabel as keyof AbstractEnumDTO ?? 'descritivo']}
            onChange={(_, value) => handleSelect(value)}
            value={props.multiple ? value : value[0] ?? null}
            blurOnSelect={true}
            noOptionsText={'Nenhum resultado encontrado'}
            isOptionEqualToValue={(option, value) => option.name === value.name}
            renderInput={
                (params) => <TextField {...params}
                    label={props.label}
                    variant="standard"
                />}
        />
    );
};

export default EnumAutocomplete;
