package uz.pdp.hrmanagement.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.hrmanagement.model.Turnpike;
import uz.pdp.hrmanagement.payload.TurnpikeDto;

@Service
public class TurnpikeMapper {

    @Autowired
    UserMapper userMapper;

    public TurnpikeDto toDto(Turnpike turnpike){
        TurnpikeDto turnpikeDto = new TurnpikeDto();
        turnpikeDto.setId(turnpike.getId());
        turnpikeDto.setEnterTime(turnpike.getEnterTime());
        turnpikeDto.setExitTime(turnpike.getExitTime());
        turnpikeDto.setDay(turnpike.getDay());
        turnpikeDto.setUserDto(userMapper.toDto(turnpike.getUser()));
        return turnpikeDto;
    }

    public Turnpike toEntity(TurnpikeDto turnpikeDto){
        Turnpike turnpike = new Turnpike();
        turnpike.setUser(userMapper.toEntity(turnpikeDto.getUserDto()));
        return turnpike;
    }

}
