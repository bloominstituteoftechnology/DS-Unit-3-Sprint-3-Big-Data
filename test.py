from typing import List, Union, Optional
import pandas as pd
DF = pd.DataFrame


def bool_to_numeric(datf: DF, feats: List[str], unknown: float = 0.5) -> DF:
  """Bool to numeric"""
  mapped = datf[feats].replace({True: 1, False: 0, 'NOT_KNOWN': unknown})
  return datf.assign(**{name: mapped[name] for name in feats})

def date_to_ord(datf: DF, feats: Union[str, List[str]]) -> DF: 
  if isinstance(feats, str): 
    feats = [feats]

  return datf.assign(**{name: lambda df: (pd
                                          .to_datetime(df[name])
                                          .apply(lambda x: x.toordinal())) 
                        for name in feats})
